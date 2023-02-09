package Backend.teampple.domain.feedbacks.repository;

import Backend.teampple.domain.feedbacks.entity.Feedback;
import Backend.teampple.domain.feedbacks.entity.FeedbackOwner;
import Backend.teampple.domain.users.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackOwnerRespository extends JpaRepository<FeedbackOwner, Long> {
    void deleteAllByFeedback(Feedback feedback);

    @Query("select fbo from FeedbackOwner fbo" +
            " join fetch fbo.feedback fb" +
            " where fbo.user = :user" +
            " order by fb.updatedAt")
    List<FeedbackOwner> findAllByUserWithFeedbackOrderByUpdatedAt(@Param("user") User user);

    @Query("select fbo from FeedbackOwner fbo" +
            " where fbo.user = :user and fbo.feedback in :feedbacks")
    List<FeedbackOwner> findAllByUserAndFeedback(@Param("user") User user, @Param("feedbacks") List<Feedback> feedbacks);

    @Query("select fbo from FeedbackOwner fbo" +
            " where fbo.user = :user")
    List<FeedbackOwner> findAllByUser(@Param("user") User authUser);
}
