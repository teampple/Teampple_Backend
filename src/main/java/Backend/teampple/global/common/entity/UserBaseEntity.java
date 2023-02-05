package Backend.teampple.global.common.entity;

import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * SAFE_DELETE!
 * 사용자 DELETE_STATUS 확인용
 */
@MappedSuperclass
@Getter
public class UserBaseEntity extends TimeBaseEntity {
    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    public void updateIsDeleted() {
        this.isDeleted = !this.isDeleted;
    }
}
