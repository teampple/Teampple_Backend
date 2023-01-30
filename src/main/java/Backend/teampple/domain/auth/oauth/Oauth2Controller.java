package Backend.teampple.domain.auth.oauth;

import Backend.teampple.domain.auth.oauth.dto.KakaoTokenDto;
import Backend.teampple.domain.auth.oauth.dto.KakaoUserDto;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "oauth 백엔드 테스트용")
public class Oauth2Controller {
    private final OauthService oauthService;
    @GetMapping("/login/oauth2/code/kakao")
    @Operation(summary = "개발용 회원가입입니다 클라이언트가 몰라도 됩니다.")
    public CommonResponse<String> developUserSign(String code) {
        KakaoTokenDto kakaoTokenDto = oauthService.getKakaoDevelop(code);
        return CommonResponse.onSuccess(HttpStatus.OK.value(),"성공");
    }
}
