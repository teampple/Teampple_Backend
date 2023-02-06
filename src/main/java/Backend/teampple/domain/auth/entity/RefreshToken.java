package Backend.teampple.domain.auth;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    private String jwtRefreshToken;
    private Date expRT;

    @Builder
    public RefreshToken(String jwtRefreshToken, Date expRT) {
        this.jwtRefreshToken = jwtRefreshToken;
        this.expRT = expRT;
    }
}
