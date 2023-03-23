package Backend.teampple.global.common.validation;

import Backend.teampple.domain.feedbacks.entity.Feedback;
import Backend.teampple.domain.feedbacks.repository.FeedbackRepository;
import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.stages.repository.StagesRepository;
import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.tasks.repository.TasksRepository;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.entity.Teammate;
import Backend.teampple.domain.teams.repository.TeammateRepository;
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

    public Team checkIsUserInTeamId(User authUser, Long teamid) {
        // 1. teammate
        Teammate teammate = teammateRepository.findAllByTeamIdAndUserWithTeam(authUser, teamid)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MISMATCH_TEAM));

        return teammate.getTeam();
    }

    public void checkIsUserInTeam(User authUser, Team team) {
        // 1. teammate
        teammateRepository.findAllByTeamAndUser(authUser, team)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.FORBIDDEN_USER));
    }

    public void checkIsUserCanPostFeedback(User authUser, Team team) {
        // 1. teammate
        teammateRepository.findAllByTeamAndUser(authUser, team)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.MISMATCH_TEAM));
    }

    public Feedback checkIsUserCanModifyFeedback(User authUser, Long feedbackId) {
        // 1. feedback + task + stage + team
        Feedback feedback = feedbackRepository.findByIdWithTaskAndStage(feedbackId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FEEDBACK_NOT_FOUND));

        // 2. teammate
        teammateRepository.findByTeamAndUser(authUser, feedback.getTask().getStage().getTeam())
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.MISMATCH_TEAM));

        return feedback;
    }

    public Task checkIsUserHaveAuthForTask(User authUser, Long taskId) {
        // 1. task + stage
        Task task = tasksRepository.findByIdWithStage(taskId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND));

        // 2. teammate
        teammateRepository.findByTeamAndUser(authUser, task.getStage().getTeam())
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.MISMATCH_TEAM));

        return task;
    }

    public Stage checkIsUserCanPostTask(User authUser, Long stageId) {
        // 1. stage
        Stage stage = stagesRepository.findById(stageId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND));

        // 2. teammate
        teammateRepository.findAllByTeamAndUser(authUser, stage.getTeam())
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.MISMATCH_TEAM));

        return stage;
    }
}
