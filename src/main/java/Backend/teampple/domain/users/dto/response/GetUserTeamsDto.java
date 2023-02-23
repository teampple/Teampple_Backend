package Backend.teampple.domain.users.dto.response;

import Backend.teampple.domain.teams.dto.response.GetTeamDto;
import Backend.teampple.domain.teams.vo.TeamNameVo;
import Backend.teampple.domain.teams.vo.TeamVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetUserTeamsDto {
    @ApiModelProperty(notes = "팀플", required = true)
    List<TeamNameVo> teams;

    @Builder
    public GetUserTeamsDto(List<TeamNameVo> teams) {
        this.teams = teams;
    }
}
