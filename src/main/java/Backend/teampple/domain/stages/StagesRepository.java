package Backend.teampple.domain.stages;

import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.teams.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StagesRepository extends JpaRepository<Stage, Long> {

    List<Stage> findAllByTeamOrderBySequenceNum(Team team);

    Optional<Stage> findAllByTeamAndSequenceNum(Team team, int sequenceNum);
}
