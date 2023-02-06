package Backend.teampple.domain.auth.oauth;

import Backend.teampple.domain.auth.dto.JwtTokenDto;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.auth.AuthUser;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "oauth 리다이렉트")
public class Oauth2Controller {
    @GetMapping("/login/oauth2/code/kakao")
    @Operation(summary = "개발용 회원가입입니다 클라이언트가 몰라도 됩니다.")
    public CommonResponse<String> developUser(String code) {
        return CommonResponse.onSuccess(HttpStatus.OK.value(),code);
    }
    @GetMapping("/oauth/kakao/success")
    @Operation(summary = "개발용 회원가입입니다 클라이언트가 몰라도 됩니다.")
    public CommonResponse<JwtTokenDto> developUserSign(@RequestParam(value = "jwtAccessToken") String jwtAccessToken, @RequestParam(value = "jwtRefreshToken") String jwtRefreshToken) {
        JwtTokenDto jwtTokenDto = JwtTokenDto.builder()
                .jwtAccessToken(jwtAccessToken)
                .jwtRefreshToken(jwtRefreshToken)
                .build();
        return CommonResponse.onSuccess(HttpStatus.OK.value(),jwtTokenDto);
    }

    @GetMapping("/login/oauth2/test")
    @Operation(summary = "개발용 회원가입입니다 클라이언트가 몰라도 됩니다.")
    public CommonResponse<String> developAuthentication(@AuthUser User user) {
        log.info("code:"+user.getAuthKey());
        return CommonResponse.onSuccess(HttpStatus.OK.value(),"ok");
    }
}
