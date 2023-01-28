package Backend.teampple.domain.users.dto.response;

import Backend.teampple.domain.teams.dto.response.GetTeamStageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetUserTasksDto {
    @ApiModelProperty(notes = "유저 명", example = "teampple", required = true)
    private String username;

    @ApiModelProperty(notes = "팀플", required = true)
    private List<GetTeamStageDto> teams;

    @Builder
    public GetUserTasksDto(String username, List<GetTeamStageDto> teams) {
        this.username = username;
        this.teams = teams;
    }
}
