package Backend.teampple.domain.teams.repository;

import Backend.teampple.domain.teams.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamsRepository extends JpaRepository<Team, Long> {

}
