package Backend.teampple.domain.tasks.entity;

import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.tasks.dto.TaskDto;
import Backend.teampple.global.common.entity.PeriodBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@DynamicInsert
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "Task")
public class Task extends PeriodBaseEntity {
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    public void convertStatus(){
        this.isDone = !this.isDone;
    }
}
