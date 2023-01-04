package Backend.teampplus.domain.tasks.entity;

import Backend.teampplus.global.common.entity.PeriodBaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Task")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor // builder 때문에 들어감
@ToString
@EqualsAndHashCode
public class Task extends PeriodBaseEntity {
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false)
    @ColumnDefault("0")
    private Boolean isDone;
}
