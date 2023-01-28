package Backend.teampple.infra.oauth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoTokenResponse {
    private String accessToken;

    private String refreshToken;

    private String idToken;
}
