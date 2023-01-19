package Backend.teampple.domain.teams.dto.response;

import Backend.teampple.domain.stages.dto.response.GetStageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class GetTeamStageDto {
    @NotNull
    @ApiModelProperty(notes = "팀플 명", example = "teampple", required = true)
    private String name;


    @NotNull
    @ApiModelProperty(notes = "단계", required = true)
    private List<GetStageDto> stages;

    @Builder
    public GetTeamStageDto(String name, List<GetStageDto> stages) {
        this.name = name;
        this.stages = stages;
    }
}
