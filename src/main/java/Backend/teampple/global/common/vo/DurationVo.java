package Backend.teampple.global.common.vo;

import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.teams.entity.Team;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DurationVo {
    @ApiModelProperty(value = "시작일", example = "2023-01-01T11:22:33", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "마감일", example = "2023-01-01T11:22:33", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDateTime dueDate;

    public static DurationVo of(Task task) {
        return DurationVo.builder()
                .startDate(task.getStartDate())
                .dueDate(task.getDueDate())
                .build();
    }

    public static DurationVo of(Team team) {
        return DurationVo.builder()
                .startDate(team.getStartDate())
                .dueDate(team.getDueDate())
                .build();
    }

    public static DurationVo of(Stage stage) {
        return DurationVo.builder()
                .startDate(stage.getStartDate())
                .dueDate(stage.getDueDate())
                .build();
    }
}
