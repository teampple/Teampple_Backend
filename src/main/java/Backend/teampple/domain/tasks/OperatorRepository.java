package Backend.teampple.domain.tasks;

import Backend.teampple.domain.tasks.entity.Operator;
import Backend.teampple.domain.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface OperatorRepository extends JpaRepository<Operator, Long> {

    @Query("select distinct o from Operator o join fetch o.userProfile where o.task = :task")
    List<Operator> findAllByTask(Task task);
}
