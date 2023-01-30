package Backend.teampple.domain.auth;

import Backend.teampple.domain.auth.dto.request.RequestJwtTokenDto;
import Backend.teampple.domain.auth.dto.response.ResponseTokenDto;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Api(tags = "사용자 인증")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인 요청", description = "로그인 요청 API/ Form 로그인 시 사용")
    public CommonResponse<String> login() {
        return CommonResponse.onSuccess(HttpStatus.OK.value(), "ok");
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃 요청", description = "로그아웃 요청 API 입니다.")
    public CommonResponse<String> logout(Authentication authentication) {
        authService.logout(authentication);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }

    @PostMapping("/info")
    @Operation(summary = "회원가입 요청", description = "회원가입 요청 API/ Form 회원가입 시 사용")
    public CommonResponse<String> signUp() {
        return CommonResponse.onSuccess(HttpStatus.OK.value(),"ok");
    }

    @DeleteMapping("/withdrawal")
    @Operation(summary = "회원탈퇴 요청", description = "회원탈퇴 요청 API 입니다.")
    public CommonResponse<String> withdrawal(Authentication authentication) {
        authService.withdrawal(authentication);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }

    @PostMapping("/reIssuance")
    @Operation(summary = "JWT access 토큰 재발급 요청", description = "JWT access 토큰 재발급 요청 API 입니다.")
    public CommonResponse<ResponseTokenDto> reIssuance(Authentication authentication, @RequestBody RequestJwtTokenDto requestJwtTokenDto){
        ResponseTokenDto responseTokenDto = authService.reIssuance(requestJwtTokenDto);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), responseTokenDto);
    }
}
