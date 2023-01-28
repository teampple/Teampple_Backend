package Backend.teampple.global.common.validation.dto;

import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.users.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserTeamDto {

    private User user;

    private Team team;

    @Builder
    public UserTeamDto(User user, Team team) {
        this.user = user;
        this.team = team;
    }
}
