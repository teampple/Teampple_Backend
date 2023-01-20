package Backend.teampple.domain.feedbacks.repository;

import Backend.teampple.domain.feedbacks.entity.Feedback;
import Backend.teampple.domain.feedbacks.entity.FeedbackOwner;
import Backend.teampple.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackOwnerRespository extends JpaRepository<FeedbackOwner, Long> {
    List<FeedbackOwner> findAllByFeedback(Feedback feedback);

    void deleteAllByFeedback(Feedback feedback);

    @Query("select fbo from FeedbackOwner fbo join fetch fbo.feedback fb join fetch fb.task order by fb.updatedAt")
    List<FeedbackOwner> findAllByUserWithFeedbackAndTaskOrderByUpdatedAt(User user);
}
