package Backend.teampplus.domain.users.entity;

import Backend.teampplus.global.common.entity.UserBaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "UserProfile")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor // builder 때문에 들어감
@ToString
@EqualsAndHashCode
public class UserProfile extends UserBaseEntity {
    @Id
    @Column(name = "user_profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false, length = 25)
    private String nickname;
    @Column(nullable = false, length = 100)
    private String email;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false, length = 50)
    private String schoolName;
    @Column(nullable = false, length = 50)
    private String major;
    @Column(length = 25)
    private String entranceYear;
    @Column(length = 25) // 이거 이넘으로 처리해야함 아직 이넘 종료 몰라서 그냥 이렇게 둠
    private String subscribePlan;


}
