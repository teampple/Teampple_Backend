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
}
