package Backend.teampple.domain.feedbacks.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PutFeedbackDto {
    @NotNull
    @ApiModelProperty(notes = "피드백", example = "싫어요", required = true)
    private String comment;
}
