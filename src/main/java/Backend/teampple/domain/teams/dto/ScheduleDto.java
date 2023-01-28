package Backend.teampple.domain.teams.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class ScheduleDto {
    @NotNull
    @ApiModelProperty(value = "팀플 이름", example = "teampple", required = true)
    private String name;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(notes = "일정 마감일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime dueDate;

    @Builder
    public ScheduleDto(String name, LocalDateTime dueDate) {
        this.name = name;
        this.dueDate = dueDate;
    }
}
