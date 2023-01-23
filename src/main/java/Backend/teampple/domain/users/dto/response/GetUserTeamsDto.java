package Backend.teampple.domain.users.dto.response;

import Backend.teampple.domain.teams.dto.response.GetTeamDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetUserTeamsDto {
    @ApiModelProperty(notes = "팀플", required = true)
    List<GetTeamDto> teams;

    @Builder
    public GetUserTeamsDto(List<GetTeamDto> teams) {
        this.teams = teams;
    }
}
