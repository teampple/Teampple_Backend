package Backend.teampplus.common.entity;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners({ AuditingEntityListener.class })
@Getter
public class PeriodBaseEntity extends TimeBaseEntity{
    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime dueDate;
}
