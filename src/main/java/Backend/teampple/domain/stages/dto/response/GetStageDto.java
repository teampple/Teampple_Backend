package Backend.teampple.domain.stages.dto.response;

import Backend.teampple.domain.stages.entity.Stage;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetStageDto {
    @ApiModelProperty(notes = "단계 명", example = "1단계", required = true)
    private String name;

    @ApiModelProperty(notes = "단계 완료 여부", example = "true", required = true)
    private boolean isDone;


    public GetStageDto(Stage stage) {
        this.name = stage.getTaskName();
        this.isDone = stage.isDone();
    }
}
