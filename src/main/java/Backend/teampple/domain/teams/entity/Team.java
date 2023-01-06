package Backend.teampple.domain.teams.entity;

import Backend.teampple.domain.teams.dto.request.PutTeamDto;
import Backend.teampple.global.common.entity.PeriodBaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Team")
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Team extends PeriodBaseEntity {
    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String name;

    @Column(nullable = false)
    private String goal;

    @Builder
    public Team(LocalDateTime startDate, LocalDateTime dueDate, Long id, String name, String goal) {
        init(startDate, dueDate);
        this.id = id;
        this.name = name;
        this.goal = goal;
    }

    public void update(PutTeamDto putTeamDto) {
        this.name = putTeamDto.getName();
        this.goal = putTeamDto.getGoal();
        init(putTeamDto.getStartDate(), putTeamDto.getDueDate());
    }
}
