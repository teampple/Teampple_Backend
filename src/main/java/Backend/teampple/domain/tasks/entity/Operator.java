package Backend.teampple.domain.tasks.entity;

import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.global.common.entity.TimeBaseEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userprofile_id") // user_profile_id 오류남
    private UserProfile userProfile;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
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
