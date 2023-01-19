package Backend.teampple.domain.teams.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetTeamDto {
    @ApiModelProperty(notes = "팀플 명", example = "teampple", required = true)
    private String name;

    @ApiModelProperty(notes = "팀플 id", example = "1", required = true)
    private Long teamId;

    @Builder
    public GetTeamDto(String name, Long teamId) {
        this.name = name;
        this.teamId = teamId;
    }
}
