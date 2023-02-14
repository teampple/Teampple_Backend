package Backend.teampple.domain.tasks.vo;

import Backend.teampple.domain.tasks.entity.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
public class TaskNameDateVo {
    @NotNull
    @ApiModelProperty(notes = "할 일 이름", example = "이름", required = true)
    private String name;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(notes = "할 일 시작일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime startDate;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(notes = "할 일 마감일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime dueDate;

    @NotNull
    @ApiModelProperty(notes = "할 일 담당자", required = true)
    private Set<Long> operators;

    public static TaskNameDateVo from(Task task, Set<Long> operators) {
        return TaskNameDateVo.builder()
                .name(task.getName())
                .startDate(task.getStartDate())
                .dueDate(task.getDueDate())
                .operators(operators)
                .build();
    }
}
