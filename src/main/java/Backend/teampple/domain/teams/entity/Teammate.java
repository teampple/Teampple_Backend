package Backend.teampple.domain.teams.entity;

import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.entity.TimeBaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Teammate")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor // builder 때문에 들어감
@ToString
@EqualsAndHashCode
public class Teammate extends TimeBaseEntity {
    @Id
    @Column(name = "teammate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

}
