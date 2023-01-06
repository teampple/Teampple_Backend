package Backend.teampple.domain.teams;

import Backend.teampple.domain.stages.StagesRepository;
import Backend.teampple.domain.stages.dto.request.PostStageDto;
import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.teams.dto.ScheduleDto;
import Backend.teampple.domain.teams.dto.request.PostScheduleDto;
import Backend.teampple.domain.teams.dto.request.PostTeamDto;
import Backend.teampple.domain.teams.dto.request.PutTeamDto;
import Backend.teampple.domain.teams.dto.response.GetScheduleDto;
import Backend.teampple.domain.teams.dto.response.GetTeamDetailDto;
import Backend.teampple.domain.teams.dto.response.GetTeamTasksDto;
import Backend.teampple.domain.teams.entity.Schedule;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.entity.Teammate;
import Backend.teampple.global.common.entity.PeriodBaseEntity;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamsService{

    private final TeamsRepository teamsRepository;

    private final StagesRepository stagesRepository;

    private final TeammateRepository teammateRepository;

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void createTeam(PostTeamDto postTeamDto) {

        // 1. 팀 생성
        Team team = Team.builder()
                .name(postTeamDto.getName())
                .goal(postTeamDto.getGoal())
                .startDate(postTeamDto.getStartDate())
                .dueDate(postTeamDto.getDueDate())
                .build();
        teamsRepository.save(team);

//        // 2. 팀 생성한 사람 팀원으로 추가
//        Teammate teammate = Teammate.builder()
//                .team(team)
//                .user(user)
//                .build();
//        teammateRepository.save(teammate);

        // 3. 단계 생성 및 저장
        List<PostStageDto> stages = postTeamDto.getStages();

        stages.forEach(postStageDto ->
                {
                    Stage stage = Stage.builder()
                            .team(team)
                            .taskName(postStageDto.getName())
                            .sequenceNum(postStageDto.getSequenceNum())
                            .startDate(postStageDto.getStartDate())
                            .dueDate(postTeamDto.getDueDate())
                            .isDone(false) // 이 부분 분명히 디폴트로 해놨는데 이거 없으면
                            .build();
                    stagesRepository.save(stage);
                }
        );
    }

    @Transactional
    public GetTeamDetailDto getTeamDetail(Long teamId) {
        // 1. team 정보 불러오기
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));

        // 2. teammate 정보 불러오기
        List<Teammate> teammates = teammateRepository.findAllByTeam(team);

        // 3. 유저가 해당 팀에 속한지 확인


        // 4. DTO 생성
        return GetTeamDetailDto.builder()
                .name(team.getName())
                .goal(team.getGoal())
                .startDate(team.getStartDate())
                .dueDate(team.getDueDate())
                .teammates(teammates)
                .build();
    }

    @Transactional
    public List<GetTeamTasksDto> getTeamTasks(Long teamId) {
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(()->new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));
        // 1. stage 조회하며 fetch 조인으로 tasks 까지
        List<Stage> stages = stagesRepository.findAllByTeam(team);
        List<GetTeamTasksDto> results = stages.stream()
                .map(obj -> new GetTeamTasksDto(obj))
                .collect(toList());

        return results;
    }

    @Transactional
    public void putTeam(PutTeamDto putTeamDto, Long teamId) {
        // 1. team 찾기
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(()->new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));

        // 2. 수정 후 저장
        team.update(putTeamDto);
        teamsRepository.save(team);
    }

    @Transactional
    public void postSchedule(PostScheduleDto postScheduleDto, Long teamId) {
        // 1. team 찾기
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(()->new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));

        // 2. 일정 생성
        Schedule schedule = Schedule.builder()
                .name(postScheduleDto.getName())
                .dueDate(postScheduleDto.getDueDate())
                .team(team)
                .build();

        // 3. 일정 저장
        scheduleRepository.save(schedule);
    }

    @Transactional
    public GetScheduleDto getSchedule(Long teamId) {
        // 1. 팀 조회
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(()->new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));

        // 2. 스케줄 조회
        List<Schedule> schedules = scheduleRepository.findAllByTeamAndDueDateIsAfterOrderByDueDate(team, LocalDateTime.now());

        // 3. 스케줄 dto 생성
        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        schedules.forEach(schedule ->
        {
            ScheduleDto converted = ScheduleDto.builder()
                    .name(schedule.getName())
                    .dueDate(schedule.getDueDate())
                    .build();
            scheduleDtoList.add(converted);
        });

        // 3. DTO 생성
        return GetScheduleDto.builder()
                .name(team.getName())
                .dueDate(team.getDueDate())
                .schedules(scheduleDtoList)
                .build();
    }
}
