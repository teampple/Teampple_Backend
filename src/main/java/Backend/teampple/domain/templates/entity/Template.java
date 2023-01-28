package Backend.teampple.domain.templates.entity;

import Backend.teampple.global.common.entity.TimeBaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "Template")
public class Template extends TimeBaseEntity {
    @Id
    @Column(name = "template_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private String urls;

    @Builder
    public Template(Long id, String name, String urls) {
        this.id = id;
        this.name = name;
        this.urls = urls;
    }
}
