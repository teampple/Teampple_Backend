package Backend.teampple.domain.invitations.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor
@RedisHash(value = "invitation") // 2시간
public class Invitation {
    @Id
    @Indexed
    private String code;

    private Long teamId;

    @TimeToLive
    private Long ttl;

    @Builder
    public Invitation(String code, Long teamId) {
        this.code = code;
        this.teamId = teamId;
        this.ttl = 7200L;
    }
}
