package Backend.teampple.domain.feedbacks.dto.response;

import Backend.teampple.domain.feedbacks.entity.FeedbackOwner;
import Backend.teampple.domain.tasks.entity.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetFeedbackBriefDto {
    @ApiModelProperty(notes = "팀플 고유번호", example = "1", required = true)
    private Long teamId;

    @ApiModelProperty(notes = "팀플 이름", example = "team", required = true)
    private String teamName;

    @ApiModelProperty(notes = "피드백 할 일 고유번호", example = "1", required = true)
    private Long taskId;

    @ApiModelProperty(notes = "피드백 할 일 이름", example = "할 일", required = true)
    private String taskName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(notes = "피드백 수정일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime modifiedAt;

    @ApiModelProperty(notes = "피드백 확인 여부", example = "true", required = true)
    private boolean isChecked;

    @Builder
    public GetFeedbackBriefDto(Long teamId, String teamName, Long taskId, String taskName,
                               LocalDateTime modifiedAt, boolean isChecked) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.taskId = taskId;
        this.taskName = taskName;
        this.modifiedAt = modifiedAt;
        this.isChecked = isChecked;
    }

    public GetFeedbackBriefDto(FeedbackOwner feedbackOwner, Task task) {
        this.teamId = task.getStage().getTeam().getId();
        this.teamName = task.getStage().getTeam().getName();
        this.taskId = feedbackOwner.getFeedback().getTask().getId();
        this.taskName = feedbackOwner.getFeedback().getTask().getName();
        this.modifiedAt = feedbackOwner.getUpdatedAt();
        this.isChecked = feedbackOwner.isChecked();
    }
}
