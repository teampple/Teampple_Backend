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
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/oauth")
@Api(tags = "oauth 백엔드 테스트용")
public class OauthController {
    private final OauthService oauthService;

    @GetMapping("/kakao/url")
    @Operation(summary = "카카오 로그인 url, 입력하면 로그인할 수 있음. 로그인 후 나오는 화면의 url에서 'code' 값을 이용해 토큰 발급 가능")
    public CommonResponse<String> kakaoUrl(){
        String response = oauthService.getKakaoUrl(true);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), response);
    }

    @GetMapping("/kakao/url/test")
    @Operation(summary = "카카오 로그인 url, 입력하면 로그인할 수 있음. 로그인 후 나오는 화면의 url에서 'code' 값을 이용해 토큰 발급 가능")
    public CommonResponse<String> kakaoUrlTest(){
        String response = oauthService.getKakaoUrl(false);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), response);
    }

    @GetMapping(value = "/kakao/login")
    @Operation(summary = "카카오 토큰 발급")
    public CommonResponse<KakaoTokenDto> kakaoLogin(@RequestParam String code) {
        KakaoTokenDto kakaoTokenDTO = oauthService.getKakaoToken(code);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), kakaoTokenDTO);
    }

    @GetMapping(value = "/kakao/userInfo")
    @Operation(summary = "카카오 토큰으로 유저 정보 조회")
    public CommonResponse<KakaoUserDto> kakaoMe(@RequestParam String token) {
        KakaoUserDto kakaoUserDTO = oauthService.getKakaoUserInfo(token);
        return CommonResponse.onSuccess(HttpStatus.OK.value(),kakaoUserDTO);
    }

    @GetMapping("/kakao/develop")
    @Operation(summary = "개발용 회원가입입니다 클라이언트가 몰라도 됩니다.")
    public ResponseEntity<KakaoTokenDto> developUserSign(@RequestParam("code") String code) {
        KakaoTokenDto kakaoTokenDto = oauthService.getKakaoDevelop(code);
        return ResponseEntity.ok()
                .body(kakaoTokenDto);
    }

    @GetMapping("/kakao/success")
    @Operation(summary = "개발용 회원가입입니다 클라이언트가 몰라도 됩니다.")
    public CommonResponse<String> developUserSign() {
        return CommonResponse.onSuccess(HttpStatus.OK.value(),"성공");
    }
}
