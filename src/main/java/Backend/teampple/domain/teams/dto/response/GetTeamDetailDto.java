package Backend.teampple.domain.teams.dto.response;

import Backend.teampple.domain.teams.entity.Teammate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class GetTeamDetailDto {
    private String name;

    private String goal;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDateTime dueDate;

    private List<Teammate> teammates;

    @Builder
    public GetTeamDetailDto(String name, String goal, LocalDateTime startDate, LocalDateTime dueDate, List<Teammate> teammates) {
        this.name = name;
        this.goal = goal;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.teammates = teammates;
    }
}
