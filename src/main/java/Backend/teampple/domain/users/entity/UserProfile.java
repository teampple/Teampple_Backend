package Backend.teampple.domain.users.entity;

import Backend.teampple.global.common.entity.UserBaseEntity;
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

    @Column(nullable = false, length = 25)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String profileImage;

    @Column(nullable = false, length = 50)
    private String schoolName;

    @Column(nullable = false, length = 50)
    private String major;

    @Column(length = 25)
    private String entranceYear;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50) default 'FreePlan'")
    private String subscribePlan;


}
