package Backend.teampple.domain.feedbacks.entity;

import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.entity.TimeBaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "Feedback")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor // builder 때문에 들어감
@ToString
@EqualsAndHashCode
@DynamicInsert
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adviser_id")
    private User adviser;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    @ColumnDefault("0")
    private boolean isChecked;
}
