package Backend.teampple.domain.tasks.repository;

import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TasksRepository extends JpaRepository<Task, Long> {
    @Query("select distinct t from Task t" +
            " join fetch t.stage" +
            " where t.id = :id")
    Optional<Task> findByIdWithStage(@Param("id") Long id);

    @Query("select distinct t from Task t" +
            " join fetch t.stage s" +
            " join fetch s.team" +
            " where t.id in :id")
   List<Task> findByIdWithStageAndTeam(@Param("id") List<Long> id);

    @Query("select distinct t from Task t where t.stage in :stages order by t.stage.id")
    List<Task> findAllByStagesOrderByStageId(@Param("stages") List<Stage> stages);
}
