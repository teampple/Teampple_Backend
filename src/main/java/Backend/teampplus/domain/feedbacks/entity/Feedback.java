package Backend.teampplus.domain.feedbacks.entity;

import Backend.teampplus.domain.tasks.entity.Task;
import Backend.teampplus.domain.users.entity.User;
import Backend.teampplus.global.common.entity.TimeBaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "Feedback")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor // builder 때문에 들어감
@ToString
@EqualsAndHashCode
public class Feedback extends TimeBaseEntity {
    @Id
    @Column(name = "feedback_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;
    @Column(nullable = false, length = 25)
    private String adviser;
    @Column(nullable = false)
    private String comment;
    @Column(nullable = false)
    @ColumnDefault("0")
    private Boolean isChecked;
}
