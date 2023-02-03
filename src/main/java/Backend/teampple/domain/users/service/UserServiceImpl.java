package Backend.teampple.domain.users.service;

import Backend.teampple.domain.feedbacks.dto.response.GetFeedbackBriefDto;
import Backend.teampple.domain.feedbacks.entity.FeedbackOwner;
import Backend.teampple.domain.feedbacks.repository.FeedbackOwnerRespository;
import Backend.teampple.domain.feedbacks.repository.FeedbackRepository;
import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.stages.repository.StagesRepository;
import Backend.teampple.domain.tasks.dto.response.GetTaskBriefDto;
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
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.InternalServerException;
import Backend.teampple.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public User createUser(UserProfile userProfile, String kakaoId) {
        User user = User.builder()
                .kakaoId(kakaoId)
                .userProfile(userProfile)
                .build();
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUserRefreshToken(User user, String refreshToken, Date expRT) {
        user.updateRefreshToken(refreshToken, expRT);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserRefreshToken(User user) {
        user.deleteRefreshToken();
        User save = userRepository.save(user);
        log.info(save.getRefreshToken() + save.getExpRT());
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userProfileService.deleteUserProfile(user.getUserProfile());
        user.updateIsDeleted();
        userRepository.save(user);
    }

    @Override
    @Transactional
    public GetUserTasksDto getUserTasks(String authUser) {
        // 1. 팀원 조회 + 유저 + 팀
        List<Teammate> teammates = teammateRepository.findAllByUserWithUserAndTeam(authUser);

        // 2. team 리스트로
        List<Team> teams = teammates.stream()
                .map(Teammate::getTeam)
                .collect(Collectors.toList());

        // 3. task 조회
        List<Stage> stages = stagesRepository.findAllByTeamsOrderByTeamId(teams);
        List<Task> tasks = tasksRepository.findAllByStagesOrderByStageId(stages);
//
        List<GetTeamStageDto> getTeamStageDtos = teams.stream()
                .map(team -> {
                    List<GetTaskBriefDto> getTaskBriefDtos = new ArrayList<>();
                    stages.stream()
                            .filter(stage -> stage.getTeam().equals(team))
                            .forEach(stage -> {
                                tasks.stream()
                                        .filter(task -> task.getStage().equals(stage))
                                        .forEach(task -> {
                                            getTaskBriefDtos.add(new GetTaskBriefDto(task));
                                        });
                            });
                    long achievement = getTaskBriefDtos.stream()
                            .filter(GetTaskBriefDto::isDone).count();
                    return GetTeamStageDto.builder()
                            .teamId(team.getId())
                            .tasks(getTaskBriefDtos)
                            .totalStage((long) getTaskBriefDtos.size())
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