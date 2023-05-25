package Backend.teampple.domain.auth;

import Backend.teampple.domain.auth.dto.request.PostJwtTokenDto;
import Backend.teampple.domain.auth.dto.response.GetJwtTokenDto;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.auth.AuthUser;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Api(tags = "사용자 인증")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인 요청", description = "로그인 요청 API/ Form 로그인 시 사용")
    public void login() {
        /**추후 추가 예정*/
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃 요청", description = "로그아웃 요청 API 입니다.")
    public void logout(@AuthUser User user, @RequestBody PostJwtTokenDto postJwtTokenDto) {
        authService.logout(user, postJwtTokenDto);
    }

    @PostMapping("/info")
    @Operation(summary = "회원가입 요청", description = "회원가입 요청 API/ Form 회원가입 시 사용")
    public void signUp() {
        /**추후 추가 예정*/
    }

    @DeleteMapping("/withdrawal")
    @Operation(summary = "회원탈퇴 요청", description = "회원탈퇴 요청 API 입니다.")
    public void withdrawal(@AuthUser User user) {
        authService.withdrawal(user);
    }

    @PostMapping("/reIssuance")
    @Operation(summary = "JWT access 토큰 재발급 요청", description = "JWT access 토큰 재발급 요청 API 입니다.")
    public GetJwtTokenDto reIssuance(@AuthUser User user, @RequestBody PostJwtTokenDto postJwtTokenDto) {
        return authService.reIssuance(user, postJwtTokenDto);
    }
}
