package Backend.teampple.domain.teams.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class PutTeamDto {

    @NotNull
    @ApiModelProperty(value = "팀플 이름", example = "teampple", required = true)
    private String name;

    @NotNull
    @ApiModelProperty(notes = "팀플 목표", example = "neck table", required = true)
    private String goal;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @ApiModelProperty(notes = "팀플 시작일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @ApiModelProperty(notes = "팀플 마감일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime dueDate;

    @Builder
    public PutTeamDto(String name, String goal, LocalDateTime startDate, LocalDateTime dueDate) {
        this.name = name;
        this.goal = goal;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }
}
