package Backend.teampple.domain.teams.vo;

import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.global.common.vo.DurationVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TeamVo {
    @JsonUnwrapped
    private TeamNameVo teamNameVo;

    @ApiModelProperty(value = "팀플 목표", example = "목포", required = true)
    private String goal;

    @JsonUnwrapped
    private DurationVo durationVo;

    public static TeamVo of(Team team) {
        return TeamVo.builder()
                .teamNameVo(TeamNameVo.of(team))
                .goal(team.getGoal())
                .durationVo(DurationVo.of(team))
                .build();
    }
}
