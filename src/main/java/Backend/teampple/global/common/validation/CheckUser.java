package Backend.teampple.global.common.validation;

import Backend.teampple.domain.feedbacks.entity.Feedback;
import Backend.teampple.domain.feedbacks.repository.FeedbackRepository;
import Backend.teampple.domain.teams.dto.UserTeamDto;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.entity.Teammate;
import Backend.teampple.domain.teams.repository.TeammateRepository;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.NotFoundException;
import Backend.teampple.global.error.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CheckUser {
    private final TeammateRepository teammateRepository;

    private final FeedbackRepository feedbackRepository;

    /*
    해당 유저가 팀에 속한지 검사하는 method
    */
    public UserTeamDto checkIsUserInTeam(String authUser, Long teamid) {
        // 1. teammate + user + team
        Teammate teammate = teammateRepository.findAllByTeamIdAndUserWithTeamAndUser(authUser, teamid)
                .orElseThrow(() -> new NotFoundException(ErrorCode._BAD_REQUEST.getMessage()));

        return UserTeamDto.builder()
                .user(teammate.getUser())
                .team(teammate.getTeam())
                .build();
    }


    public User checkIsUserCanPostFeedback(String authUser, Team team) {
        // 1. teammate + user
        Teammate teammate = teammateRepository.findAllByTeamAndUserWithUser(authUser, team)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.FORBIDDEN_USER.getMessage()));

        return teammate.getUser();
    }

    public Feedback checkIsUserCanModifyFeedback(String authUser, Long feedbackId) {
        // 1. feedback + task + stage + team
        Feedback feedback = feedbackRepository.findByIdWithTaskAndStageAndTeam(feedbackId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FEEDBACK_NOT_FOUND.getMessage()));

        // 2. teammate + user
        teammateRepository.findAllByTeamAndUser(authUser, feedback.getTask().getStage().getTeam())
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.FORBIDDEN_USER.getMessage()));

        return feedback;
    }
}
