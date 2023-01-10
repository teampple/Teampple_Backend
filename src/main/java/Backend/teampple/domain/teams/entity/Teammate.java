package Backend.teampple.domain.teams.entity;

import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.global.common.entity.TimeBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Teammate")
@Getter
@NoArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @Builder
    public Teammate(Long id, User user, Team team, UserProfile userProfile) {
        this.id = id;
        this.user = user;
        this.team = team;
        this.userProfile = userProfile;
    }
}
