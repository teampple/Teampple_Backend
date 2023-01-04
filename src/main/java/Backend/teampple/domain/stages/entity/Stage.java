package Backend.teampple.domain.stages.entity;

import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.global.common.entity.PeriodBaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Stage")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor // builder 때문에 들어감
@ToString
@EqualsAndHashCode
@DynamicInsert
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

    @Column(length = 50)
    @ColumnDefault("")
    private String lectureName;

    @ColumnDefault("")
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

}
