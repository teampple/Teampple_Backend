package Backend.teampple.domain.feedbacks.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetFeedbackBriefDto {
    @ApiModelProperty(notes = "피드백 할 일 이름", example = "할 일", required = true)
    private String taskName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(notes = "피드백 수정일", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime modifiedAt;

    @ApiModelProperty(notes = "피드백 확인 여부", example = "true", required = true)
    private boolean isChecked;

    @Builder
    public GetFeedbackBriefDto(String taskName, LocalDateTime modifiedAt, boolean isChecked) {
        this.taskName = taskName;
        this.modifiedAt = modifiedAt;
        this.isChecked = isChecked;
    }
}
