package Backend.teampple.domain.users.dto.response;

import Backend.teampple.domain.feedbacks.dto.response.GetFeedbackBriefDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class GetUserFeedbacksDto {
    @ApiModelProperty(notes = "피드백", required = true)
    List<GetFeedbackBriefDto> feedbacks;

    @Builder
    public GetUserFeedbacksDto(List<GetFeedbackBriefDto> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
