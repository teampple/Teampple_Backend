package Backend.teampple.domain.tasks.repository;

import Backend.teampple.domain.tasks.entity.Operator;
import Backend.teampple.domain.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface OperatorRepository extends JpaRepository<Operator, Long> {

    @Query("select distinct o from Operator o join fetch o.userProfile where o.task = :task")
    List<Operator> findAllByTask(Task task);

    @Query("select distinct o from Operator o join fetch o.user where o.task = :task order by o.user.id")
    List<Operator> findAllByTaskWithUserOrderByUserId(Task task);
}
