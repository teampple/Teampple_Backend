package Backend.teampple.domain.teams.vo;

import Backend.teampple.domain.teams.entity.Team;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamNameVo {
    @ApiModelProperty(value = "팀플 id", example = "teampple", required = true)
    private Long id;

    @ApiModelProperty(value = "팀플 이름", example = "teampple", required = true)
    private String name;

    public static TeamNameVo of(Team team) {
        return TeamNameVo.builder()
                .id(team.getId())
                .name(team.getName())
                .build();
    }
}
