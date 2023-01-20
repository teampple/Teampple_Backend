package Backend.teampple.global.common.validation.dto;

import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.entity.UserProfile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserStageDto {
    private User user;

    private Stage stage;

    private UserProfile userProfile;

    @Builder
    public UserStageDto(User user, Stage stage, UserProfile userProfile) {
        this.user = user;
        this.stage = stage;
        this.userProfile = userProfile;
    }
}
