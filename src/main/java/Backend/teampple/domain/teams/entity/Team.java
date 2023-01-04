package Backend.teampple.domain.teams.entity;

import Backend.teampple.global.common.entity.PeriodBaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Team")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor // builder 때문에 들어감
@ToString
@EqualsAndHashCode
public class Team extends PeriodBaseEntity {
    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String name;

    @Column(nullable = false)
    private String goal;

}
