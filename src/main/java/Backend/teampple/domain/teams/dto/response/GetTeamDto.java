package Backend.teampple.domain.teams.dto.response;

import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.vo.TeamNameVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
public class GetTeamDto {
    @JsonUnwrapped
    private TeamNameVo teamNameVo;

    public static GetTeamDto of(Team team) {
        return GetTeamDto.builder()
                .teamNameVo(TeamNameVo.of(team))
                .build();
    }
}
