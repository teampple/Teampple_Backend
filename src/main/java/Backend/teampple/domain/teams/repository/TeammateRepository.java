package Backend.teampple.domain.teams.repository;

import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.entity.Teammate;
import Backend.teampple.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeammateRepository extends JpaRepository<Teammate, Long> {
    List<Teammate> findAllByTeam(Team team);

    @Query("select tm from Teammate tm " +
            "join fetch tm.team t " +
            "where tm.user = :user and t.dueDate >= current_time")
    List<Teammate> findAllByUserWithTeamAfterNow(@Param("user") User user);

    @Query("select tm from Teammate tm " +
            "join fetch tm.team t " +
            "where tm.user = :user and t.dueDate < current_time")
    List<Teammate> findAllByUserWithTeamBeforeNow(@Param("user") User user);

    @Query("select tm from Teammate tm " +
            "where tm.user.kakaoId = :user and tm.team = :team")
    Optional<Teammate> findByUserIdAndTeam(@Param("user") String user, @Param("team") Team team);

    @Query("select tm from Teammate tm" +
            " join fetch tm.team" +
            " where tm.team.id = :teamId and tm.user = :user")
    Optional<Teammate> findAllByTeamIdAndUserWithTeam(@Param("user") User user, @Param("teamId") Long teamId);

    @Query("select tm from Teammate tm " +
            " join fetch tm.team" +
            " where tm.user = :user")
    List<Teammate> findAllByUserWithTeam(@Param("user") User user);

    @Query("select tm from Teammate tm " +
            " join fetch tm.user" +
            " where tm.team = :team")
    List<Teammate> findAllByTeamWithUser(@Param("team") Team team);

    @Query("select tm from Teammate tm" +
            " where tm.team = :team and tm.user = :user")
    Optional<Teammate> findAllByTeamAndUser(@Param("user") User User, @Param("team") Team team);

    @Query("select tm from Teammate tm" +
            " where tm.team = :team and tm.user = :user")
    Optional<Teammate> findByTeamAndUser(@Param("user") User user, @Param("team") Team team);

    @Query("select distinct tm from Teammate tm where tm.id in :id order by tm.user.id")
    List<Teammate> findAllByIdOrderByUserId(@Param("id") List<Long> id);
}
