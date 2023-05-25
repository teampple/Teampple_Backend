package Backend.teampple.domain.auth.jwt.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class JwtToken {
    private String jwtAccessToken;
    private String jwtRefreshToken;

    @Builder
    public JwtToken(String jwtAccessToken, String jwtRefreshToken, Date expRT) {
        this.jwtAccessToken = jwtAccessToken;
        this.jwtRefreshToken = jwtRefreshToken;
    }
}
