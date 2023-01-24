package Backend.teampple.domain.teams.dto.response;

import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.tasks.dto.response.GetTaskBriefDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetTeamTasksDto {
    @ApiModelProperty(value = "단계 이름", example = "계단", required = true)
    private String stageName;

    @ApiModelProperty(value = "단계 시작일", example = "2023-01-01T11:22:33", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "단계 마감일", example = "2023-01-01T11:22:33", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDateTime dueDate;

    @ApiModelProperty(value = "단계 순서", example = "3", required = true)
    private int sequenceNum;

    @ApiModelProperty(value = "할 일 수", example = "2", required = true)
    private int totaltask;

    @ApiModelProperty(value = "끝난 할 일 수", example = "1", required = true)
    private int achievement;

    @ApiModelProperty(value = "할 일", required = true)
    private List<GetTaskBriefDto> tasks;

    public GetTeamTasksDto(Stage stage) {
        stageName = stage.getTaskName();
        startDate = stage.getStartDate();
        dueDate = stage.getDueDate();
        sequenceNum = stage.getSequenceNum();
        totaltask = stage.getTotalTask();
        achievement = stage.getAchievement();
        tasks = stage.getTasks().stream()
                .map(task -> new GetTaskBriefDto(task))
                .collect(toList());
    }
}
