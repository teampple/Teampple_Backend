package Backend.teampple.domain.auth.oauth;

import Backend.teampple.domain.auth.oauth.dto.KakaoTokenDto;
import Backend.teampple.domain.auth.oauth.dto.KakaoUserDto;
import Backend.teampple.infra.oauth.KakaoOauthApi;
import Backend.teampple.infra.oauth.KakaoOauthClientApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OauthService {
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String restApiKey;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUrl;

    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String authenticationUrl;

    private final KakaoOauthApi kakaoOauthApi;

    private final KakaoOauthClientApi kakaoOauthClientApi;

    public String getKakaoUrl(boolean isTest) {
        if (isTest) {
            return authenticationUrl + "?client_id=" + restApiKey + "&redirect_uri=" +
                    "http://localhost:8080/api/oauth/kakao" + "&response_type=code";
        } else {
            return authenticationUrl + "?client_id=" + restApiKey + "&redirect_uri=" +
                    redirectUrl + "&response_type=code";
        }
    }

    public KakaoTokenDto getKakaoToken(String code) {
        return kakaoOauthApi.kakaoGetToken(restApiKey, "http://localhost:8080/api/oauth/kakao", code);
    }

    public KakaoUserDto getKakaoUserInfo(String token) {
        return kakaoOauthClientApi.kakaoGetInfo("Bearer " + token);
    }

    public KakaoTokenDto getKakaoDevelop(String code) {
        return kakaoOauthApi.kakaoGetToken(restApiKey, redirectUrl, code);
    }
}
