package Backend.teampple.domain.teams.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ScheduleDto {

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime dueDate;

    @Builder
    public ScheduleDto(String name, LocalDateTime dueDate) {
        this.name = name;
        this.dueDate = dueDate;
    }
}
