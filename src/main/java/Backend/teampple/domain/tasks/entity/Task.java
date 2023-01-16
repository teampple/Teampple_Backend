package Backend.teampple.domain.tasks.entity;

import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.tasks.dto.TaskDto;
import Backend.teampple.global.common.entity.PeriodBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Task")
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@DynamicInsert
public class Task extends PeriodBaseEntity {
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id")
    private Stage stage;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    @ColumnDefault("0")
    private boolean isDone;

    @Builder
    public Task(LocalDateTime startDate, LocalDateTime dueDate, Long id,
                Stage stage, String name, boolean isDone) {
        init(startDate, dueDate);
        this.id = id;
        this.stage = stage;
        this.name = name;
        this.isDone = isDone;
    }

    public void update(TaskDto taskDto) {
        this.name = taskDto.getName();
        init(taskDto.getStartDate(), taskDto.getDueDate());
    }
}
