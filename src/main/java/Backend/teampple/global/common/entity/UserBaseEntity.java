package Backend.teampple.global.common.entity;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/** SAFE_DELETE!
 * 생성한 사람의 DELETE_STATUS를 위해 생성하였습니다.
 * jpa의 audit(감시) 기능을 사용합니다 */
@MappedSuperclass
@EntityListeners({ AuditingEntityListener.class })
@Getter
public class UserBaseEntity extends TimeBaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50) default 'NO'")
    private DeleteStatus delStatus;

    public void changeDeleteStatus(){
        if(this.delStatus ==DeleteStatus.YES){this.delStatus =DeleteStatus.NO;}
        else{this.delStatus=DeleteStatus.YES;}
    }
}
