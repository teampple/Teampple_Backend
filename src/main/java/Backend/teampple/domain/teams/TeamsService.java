package Backend.teampple.domain.teams;

import Backend.teampple.domain.stages.repository.StagesRepository;
import Backend.teampple.domain.stages.dto.StageDto;
import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.teams.dto.ScheduleDto;
import Backend.teampple.domain.teams.dto.TeammateDto;
import Backend.teampple.domain.teams.dto.request.*;
import Backend.teampple.domain.teams.dto.response.GetScheduleDto;
import Backend.teampple.domain.teams.dto.response.GetTeamDetailDto;
import Backend.teampple.domain.teams.dto.response.GetTeamTasksDto;
import Backend.teampple.domain.teams.dto.response.GetTeammateDto;
import Backend.teampple.domain.teams.entity.Schedule;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.entity.Teammate;
import Backend.teampple.domain.teams.repository.ScheduleRepository;
import Backend.teampple.domain.teams.repository.TeammateRepository;
import Backend.teampple.domain.teams.repository.TeamsRepository;
import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamsService{

    private final TeamsRepository teamsRepository;

    private final StagesRepository stagesRepository;

    private final TeammateRepository teammateRepository;

    private final ScheduleRepository scheduleRepository;

    private final UserRepository userRepository;

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
        List<StageDto> stages = postTeamDto.getStages();

        stages.forEach(StageDto ->
                {
                    Stage stage = Stage.builder()
                            .team(team)
                            .taskName(StageDto.getName())
                            .sequenceNum(StageDto.getSequenceNum())
                            .startDate(StageDto.getStartDate())
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


        // 4. 팀원 관련 정보 변환
        teammates.size();
        List<String> teammatesImages = new ArrayList<>();
        teammates.forEach(teammate -> {
            teammatesImages.add(teammate.getUserProfile().getProfileImage());
        });

        // 5. DTO 생성
        return GetTeamDetailDto.builder()
                .name(team.getName())
                .goal(team.getGoal())
                .startDate(team.getStartDate())
                .dueDate(team.getDueDate())
                .teammatesNum(teammates.size())
                .teammatesImages(teammatesImages)
                .build();
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
    public List<GetTeamTasksDto> getTeamTasks(Long teamId) {
        // 1. 팀 조회
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(()->new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));

        // 2. 단계 불러오기
        List<Stage> stages = stagesRepository.findAllByTeamOrderBySequenceNum(team);

        // 3. 할 일 불러오기
        List<GetTeamTasksDto> results = stages.stream()
                .map(obj -> new GetTeamTasksDto(obj))
                .collect(toList());

        return results;
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
        schedules.forEach(schedule -> {
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

    @Transactional
    public void postSchedule(ScheduleDto scheduleDto, Long teamId) {
        // 1. team 찾기
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(()->new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));

        // 2. 일정 생성
        Schedule schedule = Schedule.builder()
                .name(scheduleDto.getName())
                .dueDate(scheduleDto.getDueDate())
                .team(team)
                .build();

        // 3. 일정 저장
        scheduleRepository.save(schedule);
    }

    @Transactional
    public void postTeammate(PostTeammateDto postTeammateDto) {
        // 1. 팀 조회
        Team team = teamsRepository.findById(postTeammateDto.getTeamId())
                .orElseThrow(()->new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));

        // 2. 유저 조회
//        userRepository.findById()

        // 3. 유저 정보 조회

        // 4. 팀원 추가
//        Teammate teammate = Teammate.builder()
//                .user()
//                .team(team)
//                .userProfile()
//                .build();
//        teammateRepository.save(teammate);
    }

    @Transactional
    public GetTeammateDto getTeammate(Long teamId) {
        // 1. 팀 조회
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(()->new NotFoundException(ErrorCode.TEAM_NOT_FOUND.getMessage()));

        // 2. 팀메이트 조회
        List<Teammate> teammates = teammateRepository.findAllByTeam(team);
        List<TeammateDto> teammateDtoList = new ArrayList<>();
        teammates.forEach(teammate -> {
            // 유저 아니면 추가하도록 로직 추가해야함
//            if(teammate.getId()!=user.id)
            TeammateDto converted = TeammateDto.builder()
                    .name(teammate.getUserProfile().getName())
                    .schoolName(teammate.getUserProfile().getSchoolName())
                    .major(teammate.getUserProfile().getMajor())
                    .build();
            teammateDtoList.add(converted);
        });


        // 3. 베치 이용해서 프로파일 가져오기
        // 유저 따로 입력하도록 해야함
        return GetTeammateDto.builder()
                .name("temp")
                .schoolName("temp")
                .major("temp")
                .teammates(teammateDtoList)
                .build();
    }

    @Transactional
    public void deleteTeammate(DeleteTeammateDto deleteTeammateDto, Long teamId) {
        // 1. Teammate 삭제
        teammateRepository.deleteById(deleteTeammateDto.getTeammateId());
    }
}
