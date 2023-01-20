package Backend.teampple.domain.feedbacks.repository;

import Backend.teampple.domain.feedbacks.entity.Feedback;
import Backend.teampple.domain.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("select f from Feedback f " +
            " join fetch f.adviser ad" +
            " join fetch ad.userProfile" +
            " where f.task = :task" +
            " order by f.createdAt")
    List<Feedback> findByTaskWithUserAndUserProfile(@Param("task")Task task);

    @Query("select f from Feedback f " +
            " join fetch f.task t" +
            " join fetch t.stage s" +
            " where f.id = :feedbackId")
    Optional<Feedback> findByIdWithTaskAndStage(@Param("feedbackId") Long feedbackId);
}
