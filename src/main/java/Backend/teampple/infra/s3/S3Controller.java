package Backend.teampple.infra.s3;

import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.auth.AuthUser;
import Backend.teampple.global.common.response.CommonResponse;
import Backend.teampple.infra.s3.dto.GetS3UrlDto;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/s3")
@Api(tags = "s3")
public class S3Controller {
    private final S3Service s3Service;

    @GetMapping(value = "/url")
    @Operation(summary = "presigned url 조회", description = "presigned url 조회 API 입니다.")
    public CommonResponse<GetS3UrlDto> getInvitation(@AuthUser User authUser,
                                                     @RequestParam Long taskId) {
        log.info("[api-get] presigned url");
        log.info("{}", authUser);

        GetS3UrlDto getS3UrlDto = s3Service.getS3Url(authUser, taskId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), getS3UrlDto);
    }
}
