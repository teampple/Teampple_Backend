package Backend.teampple.domain.teams.repository;

import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.entity.Teammate;
import Backend.teampple.domain.users.entity.User;
import org.hibernate.annotations.Fetch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeammateRepository extends JpaRepository<Teammate, Long> {

    List<Teammate> findAllByTeam(Team team);

    @Query("select tm from Teammate tm join fetch tm.user where tm.team = :team")
    List<Teammate> findAllByTeamWithUser(@Param("team") Team team);

    @Query("select tm from Teammate tm join fetch tm.team t where tm.user = :user and t.dueDate >= current_time")
    List<Teammate> findAllByUserWithTeamAfterNow(User user);


    @Query("select tm from Teammate tm join fetch tm.team t where tm.user = :user and t.dueDate < current_time")
    List<Teammate> findAllByUserWithTeamBeforeNow(User user);
}
