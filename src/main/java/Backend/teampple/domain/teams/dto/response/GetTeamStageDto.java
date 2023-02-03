package Backend.teampple.domain.teams.dto.response;

import Backend.teampple.domain.tasks.dto.response.GetTaskBriefDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetTeamStageDto {
    @ApiModelProperty(notes = "팀플 고유번호", example = "1", required = true)
    private Long teamId;

    @ApiModelProperty(notes = "팀플 명", example = "teampple", required = true)
    private String name;

    @ApiModelProperty(notes = "할 일", required = true)
    private List<GetTaskBriefDto> tasks;

    @ApiModelProperty(notes = "완료된 단계 수", required = true)
    private Long achievement;

    @ApiModelProperty(notes = "단계 수", required = true)
    private Long totalStage;

    @Builder
    public GetTeamStageDto(Long teamId, String name, List<GetTaskBriefDto> tasks, Long achievement, Long totalStage) {
        this.teamId = teamId;
        this.name = name;
        this.tasks = tasks;
        this.achievement = achievement;
        this.totalStage = totalStage;
    }
}
