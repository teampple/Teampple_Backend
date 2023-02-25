package Backend.teampple.domain.tasks.vo;

import Backend.teampple.domain.tasks.entity.Task;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskNameVo {
    @ApiModelProperty(value = "할 일 고유번호", example = "1", required = true)
    private Long taskId;

    @ApiModelProperty(value = "할 일 이름", example = "이름", required = true)
    private String name;

    @ApiModelProperty(value = "할 일 완료 여부", example = "false", required = true)
    private boolean isDone;

    static public TaskNameVo of(Task task) {
        return TaskNameVo.builder()
                .taskId(task.getId())
                .name(task.getName())
                .isDone(task.isDone())
                .build();
    }
}
