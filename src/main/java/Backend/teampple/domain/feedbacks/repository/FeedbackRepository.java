package Backend.teampple.domain.feedbacks.repository;

import Backend.teampple.domain.feedbacks.entity.Feedback;
import Backend.teampple.domain.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("select f from Feedback f " +
            " join fetch f.adviser ad" +
            " join fetch ad.userProfile" +
            " where f.task = :task" +
            " order by f.createdAt")
    List<Feedback> findByTask(Task task);
}
