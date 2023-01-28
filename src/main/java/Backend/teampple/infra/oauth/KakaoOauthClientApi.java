package Backend.teampple.infra.oauth;

import Backend.teampple.domain.auth.oauth.dto.KakaoUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "KakaoAuthClient",
        url = "https://kapi.kakao.com")
public interface KakaoOauthClientApi {

    @GetMapping("/v2/user/me")
    KakaoUserDto kakaoGetInfo(@RequestHeader("Authorization") String accessToken);

}
