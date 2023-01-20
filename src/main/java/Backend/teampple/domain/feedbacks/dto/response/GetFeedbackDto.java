package Backend.teampple.domain.feedbacks.dto.response;

import Backend.teampple.domain.feedbacks.entity.Feedback;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class GetFeedbackDto {

    @NotNull
    @ApiModelProperty(notes = "피드백 고유번호", example = "1", required = true)
    private Long feedbackId;

    @NotNull
    @ApiModelProperty(notes = "피드백 한 사람", example = "2023-01-01T11:22:33", required = true)
    private String adviser;

    @NotNull
    @ApiModelProperty(notes = "피드백 내용", example = "2023-01-01T11:22:33", required = true)
    private String comment;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(notes = "피드백 생성 시간", example = "2023-01-01T11:22:33", required = true)
    private LocalDateTime createdAt;

    @NotNull
    @ApiModelProperty(notes = "파일 마지막 수정일", example = "2023-01-01T11:22:33", required = true)
    private String adviserImage;

    @Builder
    public GetFeedbackDto(Long id, String adviser, String comment, LocalDateTime createdAt, String adviserImage) {
        this.feedbackId = id;
        this.adviser = adviser;
        this.comment = comment;
        this.createdAt = createdAt;
        this.adviserImage = adviserImage;
    }

    public GetFeedbackDto(Feedback feedback) {
        this.feedbackId = feedback.getId();
        this.adviser = feedback.getAdviser().getUserProfile().getName();
        this.comment = feedback.getComment();
        this.createdAt = feedback.getCreatedAt();
        this.adviserImage = feedback.getAdviser().getUserProfile().getProfileImage();
    }
}
