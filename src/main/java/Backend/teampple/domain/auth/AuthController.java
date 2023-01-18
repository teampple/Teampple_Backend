package Backend.teampple.domain.auth;

import Backend.teampple.domain.auth.dto.request.RequestJwtTokenDto;
import Backend.teampple.domain.auth.dto.request.RequestOAuthTokenDto;
import Backend.teampple.domain.auth.dto.request.RequestSignUpDto;
import Backend.teampple.domain.auth.dto.response.ResponseTokenDto;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "로그인 요청")
    @PostMapping("/login")
    public CommonResponse<ResponseTokenDto> login(@RequestBody RequestOAuthTokenDto requestOAuthTokenDto) {
        ResponseTokenDto responseTokenDto = authService.login(requestOAuthTokenDto);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), responseTokenDto);
    }

    @ApiOperation(value = "로그아웃 요청")
    @PostMapping("/logout")
    public CommonResponse<String> logout(Authentication authentication) {
        authService.logout(authentication);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }

    @ApiOperation(value = "회원가입 요청")
    @PostMapping("/info")
    public CommonResponse<ResponseTokenDto> signUp(@RequestBody RequestSignUpDto requestSignUpDto) {
        ResponseTokenDto responseTokenDto = authService.join(requestSignUpDto);
        return CommonResponse.onSuccess(HttpStatus.OK.value(),responseTokenDto);
    }

    @ApiOperation(value = "회원탈퇴 요청")
    @DeleteMapping("/withdrawal")
    public CommonResponse<String> withdrawal(Authentication authentication) {
        authService.withdrawal(authentication);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }

    @ApiOperation(value = "리프레시토큰 재발급 요청")
    @PostMapping("/reIssuance")
    public CommonResponse<ResponseTokenDto> reIssuance(Authentication authentication, @RequestBody RequestJwtTokenDto requestJwtTokenDto){
        ResponseTokenDto responseTokenDto = authService.reIssuance(requestJwtTokenDto);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), responseTokenDto);
    }
}
