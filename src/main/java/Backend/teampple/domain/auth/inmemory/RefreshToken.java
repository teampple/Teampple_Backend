package Backend.teampple.domain.auth.inmemory;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;


@Getter
@NoArgsConstructor
@RedisHash(value = "refreshToken")
public class RefreshToken {
    @Id
    @Indexed
    private String jwtRefreshToken;

    private String authKey;

    @TimeToLive
    private Long ttl;

    @Builder
    public RefreshToken(String jwtRefreshToken, String authKey) {
        this.jwtRefreshToken = jwtRefreshToken;
        this.authKey = authKey;
        this.ttl = 1000L * 60 * 60 * 24 * 14;
    }
}
