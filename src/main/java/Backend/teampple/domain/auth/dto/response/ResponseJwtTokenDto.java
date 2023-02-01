package Backend.teampple.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@ToString
@NoArgsConstructor
public class ResponseJwtTokenDto {
    private String jwtAccessToken;
    private String jwtRefreshToken;

    @Builder
    public ResponseJwtTokenDto(String jwtAccessToken, String jwtRefreshToken) {
        this.jwtAccessToken = jwtAccessToken;
        this.jwtRefreshToken = jwtRefreshToken;
    }
}
