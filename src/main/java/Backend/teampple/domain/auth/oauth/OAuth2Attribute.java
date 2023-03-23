package Backend.teampple.domain.auth.oauth;

import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.UnauthorizedException;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Getter
public class OAuth2Attribute {
    private final Map<String, Object> attributes;
    private final String attributeKey;
    private final long OAuthId;
    private final String email;
    private final String name;
    private String provider;

    public static OAuth2Attribute of(String provider, String attributeKey,
                                     Map<String, Object> attributes) {
        switch (provider) {
            case "kakao":
                return ofKakao("kakaoOauth", attributes);
            default:
                throw new UnauthorizedException(ErrorCode.INVALID_PROVIDER);
        }
    }

    private static OAuth2Attribute ofKakao(String attributeKey,
                                           Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
                .name((String) kakaoProfile.get("nickname"))
                .OAuthId((Long) attributes.get("id"))
                .email((String) kakaoAccount.get("email"))
                .provider("kakao")
                .attributes(kakaoAccount)
                .attributeKey(attributeKey)
                .build();
    }

    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeKey);
        map.put("key", attributeKey);
        map.put("name", name);
        map.put("oauthId", OAuthId);
        map.put("email", email);
        map.put("provider", provider);

        return map;
    }

    @Builder
    public OAuth2Attribute(Map<String, Object> attributes, String attributeKey, long OAuthId, String email, String name, String provider) {
        this.attributes = attributes;
        this.attributeKey = attributeKey;
        this.OAuthId = OAuthId;
        this.email = email;
        this.name = name;
        this.provider = provider;
    }
}