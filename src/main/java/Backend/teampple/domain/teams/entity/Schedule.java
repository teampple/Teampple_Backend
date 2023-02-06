package Backend.teampple.domain.teams.entity;

import Backend.teampple.global.common.entity.PeriodBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "Schedule")
public class Schedule extends PeriodBaseEntity {
    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public Schedule(LocalDateTime startDate, LocalDateTime dueDate, Long id, String name, Team team) {
        init(startDate, dueDate);
        this.id = id;
        this.name = name;
        this.team = team;
    }
}
