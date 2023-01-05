package Backend.teampple.domain.teams;

import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.entity.Teammate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeammateRepository extends JpaRepository<Teammate, Long> {
    List<Teammate> findAllByTeam(Team team);

    // teammate 만들어지면 이거 테스트 해보기
//    @Query(value = "SELECT distinct tm from Teammate tm join fetch tm.team where tm.team.id = :team ")
//    List<Teammate> findAllByTeam(Long team);
}
