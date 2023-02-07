package Backend.teampple.domain.tasks.repository;

import Backend.teampple.domain.tasks.entity.Operator;
import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.teams.entity.Teammate;
import Backend.teampple.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
    @Query("select distinct o from Operator o join fetch o.userProfile where o.task = :task")
    List<Operator> findAllByTaskWithUserProfile(@Param("task") Task task);

    @Query("select distinct o from Operator o join fetch o.user where o.task = :task order by o.user.id")
    List<Operator> findAllByTaskWithUserOrderByUserId(@Param("task") Task task);

    List<Operator> findAllByTask(@Param("task") Task task);

    List<Operator> findAllByUser(@Param("user") User authUser);
}
