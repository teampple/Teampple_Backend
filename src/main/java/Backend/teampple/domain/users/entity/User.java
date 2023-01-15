package Backend.teampple.domain.users.entity;

import Backend.teampple.global.common.entity.UserBaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Table(name = "Users")
@SQLDelete(sql = "UPDATE Users SET isDeleted = true WHERE id = ?")
@Where(clause = "isDeleted = false")
public class User extends UserBaseEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @Column()
    private String refreshToken;

    @Column()
    private LocalDateTime expRT;

    @Column(nullable = false)
    private String kakaoId;

    @Builder
    public User(Long id, UserProfile userProfile, String refreshToken, LocalDateTime expRT, String kakaoId) {
        this.id = id;
        this.userProfile = userProfile;
        this.refreshToken = refreshToken;
        this.expRT = expRT;
        this.kakaoId = kakaoId;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void deleteRefreshToken() {
        this.refreshToken = null;
        this.expRT = null;
    }
}
