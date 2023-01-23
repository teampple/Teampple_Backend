package Backend.teampple.domain.teams.dto.response;

import Backend.teampple.domain.teams.dto.ScheduleDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetScheduleDto {
    @ApiModelProperty(value = "일정 이름", example = "1정", required = true)
    private String name;

    @ApiModelProperty(value = "일정 마감일", example = "2023-01-01T11:22:33", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime dueDate;

    @ApiModelProperty(value = "일정", required = true)
    private List<ScheduleDto> schedules = new ArrayList<>();

    @Builder
    public GetScheduleDto(String name, LocalDateTime dueDate, List<ScheduleDto> schedules) {
        this.name = name;
        this.dueDate = dueDate;
        this.schedules = schedules;
    }
}
