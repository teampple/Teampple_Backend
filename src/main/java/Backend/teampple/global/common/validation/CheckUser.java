package Backend.teampple.global.common.validation;

import Backend.teampple.domain.feedbacks.entity.Feedback;
import Backend.teampple.domain.feedbacks.repository.FeedbackRepository;
import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.stages.repository.StagesRepository;
import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.tasks.repository.TasksRepository;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.validation.dto.UserStageDto;
import Backend.teampple.global.common.validation.dto.UserTaskDto;
import Backend.teampple.global.common.validation.dto.UserTeamDto;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.entity.Teammate;
import Backend.teampple.domain.teams.repository.TeammateRepository;
import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.NotFoundException;
import Backend.teampple.global.error.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckUser {
    private final TeammateRepository teammateRepository;

    private final TasksRepository tasksRepository;

    private final FeedbackRepository feedbackRepository;

    private final StagesRepository stagesRepository;

    private final UserRepository userRepository;

    /*
    해당 유저가 팀에 속한지 검사하는 method
    */
    public UserTeamDto checkIsUserInTeam(String authUser, Long teamid) {
        // 1. teammate + user
        Teammate teammate = teammateRepository.findAllByTeamIdAndUserWithTeamAndUser(authUser, teamid)
                .orElseThrow(() -> new NotFoundException(ErrorCode._BAD_REQUEST.getMessage()));

        return UserTeamDto.builder()
                .user(teammate.getUser())
                .team(teammate.getTeam())
                .build();
    }


    public void checkIsUserCanPostFeedback(User authUser, Team team) {
        // 1. teammate
        teammateRepository.findAllByTeamAndUser(authUser, team)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.FORBIDDEN_USER.getMessage()));
    }

    public Feedback checkIsUserCanModifyFeedback(User authUser, Long feedbackId) {
        // 1. feedback + task + stage + team
        Feedback feedback = feedbackRepository.findByIdWithTaskAndStage(feedbackId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FEEDBACK_NOT_FOUND.getMessage()));

        // 2. teammate
        teammateRepository.findByTeamAndUser(authUser, feedback.getTask().getStage().getTeam())
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.FORBIDDEN_USER.getMessage()));

        return feedback;
    }

    public UserTaskDto checkIsUserHaveAuthForTask(User authUser, Long taskId) {
        // 1. task + stage
        Task task = tasksRepository.findByIdWithStage(taskId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage()));

        // 2. teammate + user
        teammateRepository.findByTeamAndUser(authUser, task.getStage().getTeam())
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.FORBIDDEN_USER.getMessage()));

        return UserTaskDto.builder()
                .user(authUser)
                .task(task)
                .build();
    }


    public UserStageDto checkIsUserCanPostTask(String authUser, Long stageId) {
        // 1. stage
        Stage stage = stagesRepository.findById(stageId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage()));

        // 2. teammate + user + userprofile
        Teammate teammate = teammateRepository.findAllByTeamAndUserWithUserAndUserProfile(authUser, stage.getTeam())
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.FORBIDDEN_USER.getMessage()));

        return UserStageDto.builder()
                .stage(stage)
                .user(teammate.getUser())
                .userProfile(teammate.getUserProfile())
                .build();
    }
}
