package Backend.teampple.domain.stages.repository;

import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.teams.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StagesRepository extends JpaRepository<Stage, Long> {
    List<Stage> findAllByTeamOrderBySequenceNum(Team team);

    @Query("select s from Stage s where s.team in :teams order by s.team.id")
    List<Stage> findAllByTeamsOrderByTeamId(@Param("teams") List<Team> teams);
}
