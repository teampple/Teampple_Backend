package Backend.teampple.domain.auth.dto.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;

@Getter
@ToString
public class RequestJwtTokenDto {
    @Valid
    private String jwtAccessToken;
    @Valid
    private String jwtRefreshToken;
}
