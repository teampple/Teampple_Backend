package Backend.teampple.domain.teams.repository;

import Backend.teampple.domain.teams.dto.ScheduleDto;
import Backend.teampple.domain.teams.entity.Schedule;
import Backend.teampple.domain.teams.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // 시간 순서, 오늘 이후
    List<Schedule> findAllByTeamAndDueDateIsAfterOrderByDueDate(Team team, LocalDateTime now);
}
