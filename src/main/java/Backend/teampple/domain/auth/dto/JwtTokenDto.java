package Backend.teampple.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class JwtTokenDto {
    private String jwtAccessToken;
    private String jwtRefreshToken;
    private Date expRT;

    @Builder
    public JwtTokenDto(String jwtAccessToken, String jwtRefreshToken, Date expRT) {
        this.jwtAccessToken = jwtAccessToken;
        this.jwtRefreshToken = jwtRefreshToken;
        this.expRT = expRT;
    }
}
