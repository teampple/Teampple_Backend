package Backend.teampplus.domain.files.entity;

import Backend.teampplus.domain.tasks.entity.Task;
import Backend.teampplus.domain.teams.entity.Team;
import Backend.teampplus.domain.users.entity.User;
import Backend.teampplus.global.common.entity.TimeBaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "File")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor // builder 때문에 들어감
@ToString
@EqualsAndHashCode
public class File extends TimeBaseEntity {
    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    @Column(nullable = false)
    private String url;
    @Column(nullable = false, length = 100)
    private String filename;
    @Column(nullable = false)
    private Long size;

}
