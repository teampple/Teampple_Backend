package Backend.teampple.domain.auth.inmemory;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Getter
@NoArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 1000L * 60 * 60 * 24 * 14)
public class RefreshToken {
    @Id
    @Indexed
    private String jwtRefreshToken;

    private String authKey;

    @Builder
    public RefreshToken(String jwtRefreshToken, String authKey) {
        this.jwtRefreshToken = jwtRefreshToken;
        this.authKey = authKey;
    }
}
