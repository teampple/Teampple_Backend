package Backend.teampplus.domain.stages.entity;

import Backend.teampplus.domain.teams.entity.Team;
import Backend.teampplus.global.common.entity.PeriodBaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Stage")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor // builder 때문에 들어감
@ToString
@EqualsAndHashCode
public class Stage extends PeriodBaseEntity {
    @Id
    @Column(name = "stage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
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
    private int totalsubtask;
    @Column(nullable = false)
    @ColumnDefault("0")
    private Boolean isDone;

}
