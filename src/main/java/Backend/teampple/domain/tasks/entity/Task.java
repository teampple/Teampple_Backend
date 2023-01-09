package Backend.teampple.domain.tasks.entity;

import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.global.common.entity.PeriodBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "Task")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor // builder 때문에 들어감
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
}
