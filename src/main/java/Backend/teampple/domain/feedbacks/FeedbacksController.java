package Backend.teampple.domain.feedbacks;

import Backend.teampple.domain.feedbacks.dto.request.PostFeedbackDto;
import Backend.teampple.domain.feedbacks.dto.request.PutFeedbackDto;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.auth.AuthUser;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/feedbacks")
@Api(tags = "피드백")
public class FeedbacksController {
    private final FeedbacksService feedbacksService;

    @PostMapping(value = "")
    @Operation(summary = "피드백 생성", description = "피드백 생성 API 입니다.")
    public void postFeedback(@AuthUser User authUser,
                                          @Valid @RequestBody PostFeedbackDto postFeedbackDto,
                                          @RequestParam("taskId") Long taskId) {
        log.info("[api-post] 피드백 생성");
        log.info("{}", authUser);

        feedbacksService.postFeedback(authUser, postFeedbackDto, taskId);
    }

    @PutMapping(value = "")
    @Operation(summary = "피드백 수정", description = "피드백 수정 API 입니다.")
    public void putFeedback(@AuthUser User authUser,
                                          @Valid @RequestBody PutFeedbackDto putFeedbackDto,
                                          @RequestParam("feedbackId") Long feedbackId) {
        log.info("[api-put] 피드백 수정");
        log.info("{}", authUser);

        feedbacksService.putFeedback(authUser, putFeedbackDto, feedbackId);
    }

    @DeleteMapping(value = "")
    @Operation(summary = "피드백 삭제", description = "피드백 삭제 API 입니다.")
    public void deleteFeedback(@AuthUser User authUser,
                                                 @RequestParam("feedbackId") Long feedbackId) {

        log.info("[api-delete] 피드백 삭제");
        log.info("{}", authUser);

        feedbacksService.deleteFeedback(authUser, feedbackId);
    }
}
