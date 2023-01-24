package Backend.teampple.domain.tasks.dto.response;

import Backend.teampple.domain.tasks.entity.Task;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class GetTaskBriefDto{
    @ApiModelProperty(value = "할 일 고유번호", example = "1", required = true)
    private Long taskId;

    @ApiModelProperty(value = "할 일 이름", example = "이름", required = true)
    private String name;

    @ApiModelProperty(value = "할 일 완료 여부", example = "false", required = true)
    private boolean isDone;

    public GetTaskBriefDto(Task task) {
        this.taskId = task.getId();
        this.name = task.getName();
        this.isDone = task.isDone();
    }
}
