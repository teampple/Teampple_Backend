package Backend.teampple.domain.stages;

import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.teams.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StagesRepository extends JpaRepository<Stage, Long> {
}
