package Backend.teampple.domain.auth;

import Backend.teampple.domain.users.dto.request.PostUserProfileDto;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Api(tags = "회원 인증")
public class AuthController {
    private final AuthService authService;

//    @PostMapping("/login")
//    public CommonResponse<> login(){
//        //jwt 토큰발급 및 전송
//    }

    @PostMapping("/logout")
    public CommonResponse<String> logout(){
        //
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }

    @PostMapping("/info")
    public CommonResponse<String> signUp(@RequestBody PostUserProfileDto postUserProfileDto){
        authService.signUp(postUserProfileDto);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }

    @DeleteMapping("/withdrawal")
    public CommonResponse<String> withdrawal(@RequestBody String refreshToken){
        authService.withdrawal(refreshToken);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }
}
