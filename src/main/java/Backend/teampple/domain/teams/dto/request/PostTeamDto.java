package Backend.teampple.domain.teams.dto.request;

import Backend.teampple.domain.stages.dto.StageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class PostTeamDto {
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

    @Valid
    @Nullable
    @ApiModelProperty(value = "팀플 이름", required = true)
    private List<StageDto> stages;

    @Builder
    public PostTeamDto(String name, String goal, LocalDateTime startDate, LocalDateTime dueDate, @Nullable List<StageDto> stages) {
        this.name = name;
        this.goal = goal;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.stages = stages;
    }
}
