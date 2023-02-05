package Backend.teampple.domain.users.entity;

import Backend.teampple.global.common.entity.UserBaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Table(name = "user_profile")
//@SQLDelete(sql = "UPDATE user_profile SET is_deleted = true WHERE kakao_id = ?")
public class UserProfile extends UserBaseEntity {
    @Id
    @Column(name = "user_profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String name;

    @Column(length = 100)
    private String email;

    @Column(nullable = false)
    private String profileImage;

    @Column(length = 50)
    private String schoolName;

    @Column(length = 50)
    private String major;

    @Column(length = 25)
    private String entranceYear;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50) default 'FreePlan'")
    private SubscriptionType subscribePlan;

    @Builder
    public UserProfile(Long id, String name, String email, String profileImage, String schoolName, String major, String entranceYear, SubscriptionType subscribePlan) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileImage = profileImage;
        this.schoolName = schoolName;
        this.major = major;
        this.entranceYear = entranceYear;
        this.subscribePlan = Objects.requireNonNullElse(subscribePlan, SubscriptionType.FreePlan);
    }
}
