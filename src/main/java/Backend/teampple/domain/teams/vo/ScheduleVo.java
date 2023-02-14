package Backend.teampple.domain.teams.vo;

import Backend.teampple.domain.teams.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
public class ScheduleVo {
    @NotNull
    @ApiModelProperty(value = "팀플 이름", example = "teampple", required = true)
    private String name;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(notes = "일정 마감일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime dueDate;

    public static ScheduleVo of(Schedule schedule) {
        return ScheduleVo.builder()
                .name(schedule.getName())
                .dueDate(schedule.getDueDate())
                .build();
    }
}
