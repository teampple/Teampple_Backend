package Backend.teampple.domain.users.service;

import Backend.teampple.domain.feedbacks.dto.response.GetFeedbackBriefDto;
import Backend.teampple.domain.feedbacks.entity.Feedback;
import Backend.teampple.domain.feedbacks.entity.FeedbackOwner;
import Backend.teampple.domain.feedbacks.repository.FeedbackOwnerRespository;
import Backend.teampple.domain.feedbacks.repository.FeedbackRepository;
import Backend.teampple.domain.stages.dto.response.GetStageDto;
import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.stages.repository.StagesRepository;
import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.tasks.repository.TasksRepository;
import Backend.teampple.domain.teams.dto.response.GetTeamDto;
import Backend.teampple.domain.teams.dto.response.GetTeamStageDto;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.entity.Teammate;
import Backend.teampple.domain.teams.repository.TeammateRepository;
import Backend.teampple.domain.teams.repository.TeamsRepository;
import Backend.teampple.domain.users.dto.response.GetUserFeedbacksDto;
import Backend.teampple.domain.users.dto.response.GetUserTasksDto;
import Backend.teampple.domain.users.dto.response.GetUserTeamsDto;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.global.common.validation.CheckUser;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.InternalServerException;
import Backend.teampple.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserProfileService userProfileService;
    private final TeamsRepository teamsRepository;
    private final StagesRepository stagesRepository;
    private final TeammateRepository teammateRepository;
    private final FeedbackOwnerRespository feedbackOwnerRespository;
    private final TasksRepository tasksRepository;
    private final FeedbackRepository feedbackRepository;
    private final CheckUser checkUser;

    @Override
    @Transactional
    public void createUser(UserProfile userProfile, String kakaoId, String refreshToken) {
        User user = User.builder()
                .userProfile(userProfile)
                .refreshToken(refreshToken)
                //TODO: 시간 주입 방식 변경 필요
                .expRT(LocalDateTime.now().plusWeeks(2L))
                .kakaoId(kakaoId)
                .build();
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUserRefreshToken(String kakaoId, String refreshToken) {
        User user = userRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저 입니다"));
        user.updateRefreshToken(refreshToken,LocalDateTime.now().plusWeeks(2L));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserRefreshToken(String kakaoId) {
        User user = userRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));
        user.deleteRefreshToken();
        User save = userRepository.save(user);
        log.info(save.getRefreshToken()+save.getExpRT());
    }

    @Override
    @Transactional
    public void deleteUser(String kakaoId){
        User user = userRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));
        userProfileService.deleteUserProfile(user.getUserProfile());
        user.updateIsDeleted();
        userRepository.save(user);
    }

    @Override
    @Transactional
    public GetUserTasksDto getUserTasks(String authUser){
        // 1. 팀원 조회 + 유저 + 팀
        List<Teammate> teammates = teammateRepository.findAllByUserWithUserAndTeam(authUser);

        // 2. team 리스트로
        List<Team> teams = teammates.stream()
                .map(Teammate::getTeam)
                .collect(Collectors.toList());

        // 3. stage 조회
        List<GetTeamStageDto> getTeamStageDtos = teams.stream()
                .map(team -> {
                    List<Stage> stages = stagesRepository.findAllByTeam(team);
                    List<GetStageDto> getStageDtos = stages.stream()
                            .map(GetStageDto::new)
                            .collect(Collectors.toList());
                    long achievement = stages.stream().filter(Stage::isDone).count();
                    return GetTeamStageDto.builder()
                            .stages(getStageDtos)
                            .totalStage((long)getStageDtos.size())
                            .achievement(achievement)
                            .name(team.getName())
                            .build();
        }).collect(Collectors.toList());

        return GetUserTasksDto.builder()
                .username(teammates.get(0).getUser().getUserProfile().getName())
                .teams(getTeamStageDtos)
                .build();
    }

    public GetUserTeamsDto getUserTeams(String authUser, boolean isActive) {
        // 1. user 조회
        User user = userRepository.findByKakaoId(authUser)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

        // 2. teammate 조회하면서 team까지
        List<Teammate> teammates;
        if (isActive) {
            teammates = teammateRepository.findAllByUserWithTeamAfterNow(user);
        } else {
            teammates = teammateRepository.findAllByUserWithTeamBeforeNow(user);
        }

        // 3. dto 생성
        List<GetTeamDto> getTeamDtos = teammates.stream()
                .map(teammate -> GetTeamDto.builder()
                        .name(teammate.getTeam().getName())
                        .teamId(teammate.getTeam().getId())
                        .build())
                .collect(Collectors.toList());

        return GetUserTeamsDto.builder()
                .teams(getTeamDtos)
                .build();
    }

    public GetUserFeedbacksDto getUserFeedbacks(String authUser) {
        // 1. user 조회
        User user = userRepository.findByKakaoId(authUser)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));

        // 2. feedback owner 조회
        List<FeedbackOwner> feedbackOwners = feedbackOwnerRespository.findAllByUserWithFeedbackOrderByUpdatedAt(user);

        // 3. task + stage + team
        List<Long> tasksId = feedbackOwners.stream()
                .map(feedbackOwner -> feedbackOwner.getFeedback().getTask().getId())
                .collect(Collectors.toList());
        List<Long> uniqueTasksId = tasksId.stream().distinct().collect(Collectors.toList());
        List<Task> tasks = tasksRepository.findByIdWithStageAndTeam(uniqueTasksId);

        // 4. feedback, team명 매칭
        List<GetFeedbackBriefDto> feedbacks = feedbackOwners.stream()
                .map(feedbackOwner -> {
                    Task t = tasks.stream()
                            .filter(task -> feedbackOwner.getFeedback().getTask().equals(task))
                            .findFirst()
                            .orElseThrow(InternalServerException::new);
                    return new GetFeedbackBriefDto(feedbackOwner, t);
                })
                .collect(Collectors.toList());



        return GetUserFeedbacksDto.builder()
                .feedbacks(feedbacks)
                .build();
    }
}