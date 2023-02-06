package Backend.teampple.domain.teams.entity;

import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.global.common.entity.TimeBaseEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "Teammate")
public class Teammate extends TimeBaseEntity {
    @Id
    @Column(name = "teammate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
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
