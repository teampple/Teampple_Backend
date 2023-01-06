package Backend.teampple.domain.stages;

import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.teams.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StagesRepository extends JpaRepository<Stage, Long> {

    List<Stage> findAllByTeam(Team team);

    @Query("select s from Stage s join fetch s.tasks where s.team = :team")
    List<Stage> findAllAndTaskByTeam(Team team);
}
