package Backend.teampple.domain.auth.oauth;

import Backend.teampple.domain.auth.oauth.dto.KakaoTokenDto;
import Backend.teampple.domain.auth.oauth.dto.KakaoUserDto;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OauthService {
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String restApiKey;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUrl;

    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String authenticationUrl;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUrl;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUrl;

    public String getKakaoUrl() {
        return authenticationUrl + "?client_id=" + restApiKey + "&redirect_uri=" +
                redirectUrl + "&response_type=code";
    }

    public KakaoTokenDto getKakaoToken(String code) {
        String getTokenURL = tokenUrl + "?grant_type=authorization_code&client_id=" + restApiKey
                        + "&redirect_uri=" + redirectUrl + "&code=" + code;
        WebClient.ResponseSpec responseSpec = WebClient.create().post().uri(getTokenURL).retrieve();

        try {
            return responseSpec.bodyToMono(KakaoTokenDto.class).block();
        } catch (Exception e) {
            throw new BadRequestException(ErrorCode.KAKAO_CODE_ERROR.getMessage());
        }
    }

    public KakaoUserDto getKakaoUserInfo(String token) {
        WebClient.ResponseSpec responseSpec = WebClient.create().get().uri(userInfoUrl)
                .header("Authorization", "Bearer " + token).retrieve();

        try {
            return responseSpec.bodyToMono(KakaoUserDto.class).block();
        } catch (Exception e) {
            throw new BadRequestException(ErrorCode.KAKAO_TOKEN_ERROR.getMessage());
        }
    }
}
