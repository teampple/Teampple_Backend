package Backend.teampplus.domain.users.entity;

import Backend.teampplus.global.common.entity.UserBaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "User")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor // builder 때문에 들어감
@ToString
@EqualsAndHashCode
public class User extends UserBaseEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String refreshToken;
    @Column(nullable = false)
    private LocalDateTime expRT;
    @Column(nullable = false)
    private String kakaoId;

}
