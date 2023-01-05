package Backend.teampple.domain.stages.entity;

import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.global.common.entity.PeriodBaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Stage")
@Getter
@ToString(exclude = "tasks")
@EqualsAndHashCode
@DynamicInsert
@NoArgsConstructor
public class Stage extends PeriodBaseEntity {
    @Id
    @Column(name = "stage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "stage")
    private List<Task> tasks = new ArrayList<>();

    @Column(nullable = false, length = 100)
    private String taskName;

    @Column(columnDefinition = "varchar(50) default '")
    private String lectureName;

    @Column(columnDefinition = "varchar(255) default '")
    private String goal;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int achievement;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int totalTask;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Boolean isDone;

    @Column(nullable = false)
    private int sequenceNum;

    @Builder
    public Stage(Long id, Team team, List<Task> tasks, String taskName,
                 String lectureName, String goal, int achievement, int totalTask,
                 Boolean isDone, int sequenceNum, LocalDateTime startDate, LocalDateTime dueDate) {
        init(startDate, dueDate);
        this.id = id;
        this.team = team;
        this.tasks = tasks;
        this.taskName = taskName;
        this.lectureName = lectureName;
        this.goal = goal;
        this.achievement = achievement;
        this.totalTask = totalTask;
        this.isDone = isDone;
        this.sequenceNum = sequenceNum;
    }
}
