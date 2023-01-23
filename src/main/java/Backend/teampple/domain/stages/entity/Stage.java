package Backend.teampple.domain.stages.entity;

import Backend.teampple.domain.stages.dto.StageDto;
import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.global.common.entity.PeriodBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString(exclude = "tasks")
@DynamicInsert
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "Stage")
public class Stage extends PeriodBaseEntity {
    @Id
    @Column(name = "stage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @JsonIgnore
    @OneToMany(mappedBy = "stage")
    private List<Task> tasks = new ArrayList<>();

    @Column(nullable = false, length = 100)
    private String taskName;

    @Column(columnDefinition = "varchar(50) default ''")
    private String lectureName;

    @Column(columnDefinition = "varchar(255) default ''")
    private String goal;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int achievement;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int totalTask;

    @Column(columnDefinition = "boolean default false")
    private boolean isDone = false;

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

    public void update(StageDto stageDto) {
        this.sequenceNum = stageDto.getSequenceNum();
        this.taskName = stageDto.getName();
        init(stageDto.getStartDate(), stageDto.getDueDate());
    }
}
