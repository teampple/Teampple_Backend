package Backend.teampple.domain.teams;

import Backend.teampple.domain.teams.entity.Teammate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeammateRepository extends JpaRepository<Teammate, Long> {
}
