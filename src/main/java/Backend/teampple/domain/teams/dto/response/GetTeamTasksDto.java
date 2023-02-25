package Backend.teampple.domain.teams.dto.response;

import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.tasks.vo.TaskNameVo;
import Backend.teampple.global.common.vo.DurationVo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetTeamTasksDto {
    @ApiModelProperty(value = "단계 고유번호", example = "1", required = true)
    private Long stageId;

    @ApiModelProperty(value = "단계 이름", example = "계단", required = true)
    private String stageName;

    @JsonUnwrapped
    private DurationVo durationVo;

    @ApiModelProperty(value = "단계 순서", example = "3", required = true)
    private int sequenceNum;

    @ApiModelProperty(value = "할 일 수", example = "2", required = true)
    private int totaltask;

    @ApiModelProperty(value = "끝난 할 일 수", example = "1", required = true)
    private int achievement;

    private List<TaskNameVo> tasks;

    public GetTeamTasksDto(Stage stage) {
        stageId = stage.getId();
        stageName = stage.getTaskName();
        durationVo = DurationVo.of(stage);
        sequenceNum = stage.getSequenceNum();
        totaltask = stage.getTotalTask();
        achievement = stage.getAchievement();
        tasks = stage.getTasks().stream()
                .map(TaskNameVo::of)
                .collect(toList());
    }
}
