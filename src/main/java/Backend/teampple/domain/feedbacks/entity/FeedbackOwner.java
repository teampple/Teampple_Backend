package Backend.teampple.domain.feedbacks.entity;

import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.global.common.entity.TimeBaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@DynamicInsert
@Table(name = "FeedbackOwner")
public class FeedbackOwner extends TimeBaseEntity {
    @Id
    @Column(name = "feedback_owner_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userprofile_id")
    private UserProfile userProfile;


    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;

    @Column(nullable = false)
    @ColumnDefault("0")
    private boolean isChecked;

    @Builder
    public FeedbackOwner(Long id, User user, UserProfile userProfile, Feedback feedback, boolean isChecked) {
        this.id = id;
        this.user = user;
        this.userProfile = userProfile;
        this.feedback = feedback;
        this.isChecked = isChecked;
    }

    public void updateCheckStatus(){
        this.isChecked = true;
    }
}
