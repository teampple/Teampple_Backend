package Backend.teampple.global.common.entity;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class PeriodBaseEntity extends TimeBaseEntity{
    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime dueDate;

    // builder로 초기화 불가능하므로, 자식 클래스 생성자에서 아래 함수를 사용하여 위 변수를 초기화해야함
    public void init(LocalDateTime startDate, LocalDateTime dueDate) {
        this.startDate = startDate;
        this.dueDate = dueDate;
    }
}
