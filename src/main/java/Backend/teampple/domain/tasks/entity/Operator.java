package Backend.teampple.domain.tasks.entity;

import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.global.common.entity.TimeBaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Operator")
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Operator extends TimeBaseEntity {
    @Id
    @Column(name = "operator_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userprofile_id") // user_profile_id 오류남
    private UserProfile userProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @Builder
    public Operator(Long id, User user, UserProfile userProfile, Task task) {
        this.id = id;
        this.user = user;
        this.userProfile = userProfile;
        this.task = task;
    }
}
