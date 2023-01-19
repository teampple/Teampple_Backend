package Backend.teampple.global.common.validation;

import Backend.teampple.domain.teams.dto.UserTeamDto;
import Backend.teampple.domain.teams.entity.Teammate;
import Backend.teampple.domain.teams.repository.TeammateRepository;
import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckUser {
    private final TeammateRepository teammateRepository;

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
}
