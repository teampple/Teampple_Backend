package Backend.teampple.domain.users.entity;

import Backend.teampple.global.common.entity.UserBaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@Table(name = "Users")
public class User extends UserBaseEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime expRT;

    @Column(nullable = false)
    private String kakaoId;
}
