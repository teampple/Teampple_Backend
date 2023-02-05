package Backend.teampple.domain.feedbacks.entity;

import Backend.teampple.domain.feedbacks.dto.request.PutFeedbackDto;
import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.entity.TimeBaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@ToString
@DynamicInsert
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "Feedback")
public class Feedback extends TimeBaseEntity {
    @Id
    @Column(name = "feedback_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adviser_id")
    private User adviser;

    @Column(nullable = false)
    private String comment;

    @Builder
    public Feedback(Long id, Task task, User adviser, String comment) {
        this.id = id;
        this.task = task;
        this.adviser = adviser;
        this.comment = comment;
    }

    public void update(PutFeedbackDto putFeedbackDto) {
        this.comment = putFeedbackDto.getComment();
    }
}
