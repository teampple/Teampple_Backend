package Backend.teampple.domain.feedbacks.repository;

import Backend.teampple.domain.feedbacks.entity.Feedback;
import Backend.teampple.domain.feedbacks.entity.FeedbackOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackOwnerRespository extends JpaRepository<FeedbackOwner, Long> {
    List<FeedbackOwner> findAllByFeedback(Feedback feedback);

    void deleteAllByFeedback(Feedback feedback);
}
