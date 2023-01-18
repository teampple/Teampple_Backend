package Backend.teampple.domain.users.service;

import Backend.teampple.domain.stages.StagesService;
import Backend.teampple.domain.stages.dto.response.GetStageDto;
import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.stages.repository.StagesRepository;
import Backend.teampple.domain.tasks.repository.TasksRepository;
import Backend.teampple.domain.teams.dto.UserTeammateDto;
import Backend.teampple.domain.teams.dto.response.GetTeamStageDto;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.repository.TeammateRepository;
import Backend.teampple.domain.teams.repository.TeamsRepository;
import Backend.teampple.domain.users.dto.response.GetUserTasksDto;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.global.common.validation.CheckUser;
import Backend.teampple.global.error.ErrorCode;
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
    public GetUserTasksDto getUserTasks(String authUser, Long teamId){
        // 1. team 조회
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));

        // 2. 유저 체크
        UserTeammateDto userTeammateDto = checkUser.checkIsUserInTeam(authUser, team);

        // 3. team 조회
        List<Team> teams = userTeammateDto.getTeammate().stream()
                .map(teammate -> teammate.getTeam())
                .collect(Collectors.toList());

        // 4. stage 조회
        List<GetTeamStageDto> getTeamStageDtos = teams.stream().map(t -> {
            List<GetStageDto> getStageDtos = stagesRepository.findAllByTeam(t)
                    .stream()
                    .map(stage -> new GetStageDto(stage))
                    .collect(Collectors.toList());
            return GetTeamStageDto.builder()
                    .stages(getStageDtos)
                    .name(t.getName())
                    .build();
        }).collect(Collectors.toList());

        return GetUserTasksDto.builder()
                .username(userTeammateDto.getUser().getUserProfile().getName())
                .teams(getTeamStageDtos)
                .build();
    }
}