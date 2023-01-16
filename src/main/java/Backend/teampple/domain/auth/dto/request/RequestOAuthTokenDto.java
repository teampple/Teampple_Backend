package Backend.teampple.domain.auth.dto.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;


@Getter
@ToString
public class RequestOAuthTokenDto {
    @Valid
    private String idToken;
    @Valid
    private String oauthAccessToken;
    @Valid
    private String oauthRefreshToken;
}
