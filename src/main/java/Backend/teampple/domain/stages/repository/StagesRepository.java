package Backend.teampple.domain.stages.repository;

import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.teams.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StagesRepository extends JpaRepository<Stage, Long> {

    List<Stage> findAllByTeamOrderBySequenceNum(Team team);

    Optional<Stage> findAllByTeamAndSequenceNum(Team team, int sequenceNum);

    List<Stage> findAllByTeam(Team team);

    @Query("select s from Stage s join fetch s.team where s.id = :stageId")
    Optional<Stage> findByIdWithTeam(Long stageId);
}
