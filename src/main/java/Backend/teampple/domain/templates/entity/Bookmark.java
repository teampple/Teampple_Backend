package Backend.teampple.domain.templates.entity;

import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.entity.TimeBaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Bookmark")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor // builder 때문에 들어감
@ToString
@EqualsAndHashCode
public class Bookmark extends TimeBaseEntity {
    @Id
    @Column(name = "bookmark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template template;
}
