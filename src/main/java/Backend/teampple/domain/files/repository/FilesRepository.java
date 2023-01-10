package Backend.teampple.domain.files.repository;

import Backend.teampple.domain.files.dto.response.GetFileDto;
import Backend.teampple.domain.files.entity.File;
import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.teams.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilesRepository extends JpaRepository<File, Long> {
    List<File> findAllByTaskOrderByUpdatedAt(Task task);

    @Query("select distinct f from File f " +
            " join fetch f.task" +
            " join fetch f.user u" +
            " join fetch u.userProfile" +
            " where f.team = :team" +
            " order by f.updatedAt desc")
    List<File> findAllWithTeamAndUserByTeam(@Param("team")Team team);
}
