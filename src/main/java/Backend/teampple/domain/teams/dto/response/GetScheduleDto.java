package Backend.teampple.domain.teams.dto.response;

import Backend.teampple.domain.teams.dto.ScheduleDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class GetScheduleDto {
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime dueDate;

    private List<ScheduleDto> schedules = new ArrayList<>();

    @Builder
    public GetScheduleDto(String name, LocalDateTime dueDate, List<ScheduleDto> schedules) {
        this.name = name;
        this.dueDate = dueDate;
        this.schedules = schedules;
    }
}
