package Backend.teampple.domain.teams.dto;

import Backend.teampple.domain.teams.entity.Teammate;
import Backend.teampple.domain.users.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserTeammateDto {

    private User user;

    private List<Teammate> teammate;

    @Builder
    public UserTeammateDto(User user, List<Teammate> teammate) {
        this.user = user;
        this.teammate = teammate;
    }
}
