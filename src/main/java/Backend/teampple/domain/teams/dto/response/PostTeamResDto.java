package Backend.teampple.domain.teams.dto.response;

import Backend.teampple.domain.teams.entity.Team;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class PostTeamResDto {
    @ApiModelProperty(value = "생성 팀 고유번호", required = true)
    private Long teamId;

    public PostTeamResDto(Team team) {
        this.teamId = team.getId();
    }
}
