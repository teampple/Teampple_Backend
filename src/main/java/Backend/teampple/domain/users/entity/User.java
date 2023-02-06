package Backend.teampple.domain.users.entity;

import Backend.teampple.global.common.entity.UserBaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Table(name = "users")
//@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE kakao_id = ?")
// TODO: User_Role 추가 예정 및 redis 적용 예정
public class User extends UserBaseEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50) default 'ROLE_USER'")
    private UserRole userRole;

    @Column(nullable = false, unique = true)
    private String authKey;

    @Column
    private String password;

    @Builder
    public User(Long id, UserProfile userProfile, UserRole userRole, String authKey, String password) {
        this.id = id;
        this.userProfile = userProfile;
        this.userRole = Objects.requireNonNullElse(userRole,UserRole.ROLE_USER);
        this.authKey = authKey;
        this.password = password;
    }

//    /**서비스 로직*/
//    public void updateRefreshToken(String refreshToken, Date expRT) {
//        this.refreshToken = refreshToken;
//        this.expRT = expRT;
//    }
//
//    public void deleteRefreshToken() {
//        this.refreshToken = null;
//        this.expRT = null;
//    }
}
