package Backend.teampple.domain.teams;

import Backend.teampple.domain.teams.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public interface TeamsRepository extends JpaRepository<Team, Long> {

}
