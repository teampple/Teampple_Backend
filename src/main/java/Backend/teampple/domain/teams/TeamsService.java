package Backend.teampple.domain.teams;

import Backend.teampple.domain.stages.repository.StagesRepository;
import Backend.teampple.domain.stages.dto.StageDto;
import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.teams.dto.ScheduleDto;
import Backend.teampple.domain.teams.dto.TeammateDto;
import Backend.teampple.domain.teams.dto.response.*;
import Backend.teampple.domain.teams.dto.request.*;
import Backend.teampple.domain.teams.entity.Schedule;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.entity.Teammate;
import Backend.teampple.domain.teams.repository.ScheduleRepository;
import Backend.teampple.domain.teams.repository.TeammateRepository;
import Backend.teampple.domain.teams.repository.TeamsRepository;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.validation.CheckUser;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamsService{

    private final TeamsRepository teamsRepository;

    private final StagesRepository stagesRepository;

    private final TeammateRepository teammateRepository;

    private final ScheduleRepository scheduleRepository;

    private final CheckUser checkUser;

    @Transactional
    public GetTeamDetailDto getTeamDetail(User authUser, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeamId(authUser, teamId);

        // 2. 팀원 조회
        List<Teammate> teammates = teammateRepository.findAllByTeam(team);

        // 3. 팀원 관련 정보 변환
        List<String> teammatesImages = teammates.stream()
                .map(teammate -> teammate.getUserProfile().getProfileImage())
                .collect(toList());

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
    public PostTeamResDto createTeam(User authUser, PostTeamDto postTeamDto) {
        // 1. 단계 request
        List<StageDto> stages = postTeamDto.getStages();
        if (stages == null) {
            throw new BadRequestException(ErrorCode.NEED_STAGE.getMessage());
        }

        // 2. 팀 생성
        Team team = new Team(postTeamDto);
        teamsRepository.save(team);

        // 3. 팀 생성한 사람 팀원으로 추가
        Teammate teammate = Teammate.builder()
                .team(team)
                .user(authUser)
                .userProfile(authUser.getUserProfile())
                .build();
        teammateRepository.save(teammate);

        // 4. 단계 생성 및 저장
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

        return new PostTeamResDto(team);
    }

    @Transactional
    public void putTeam(User authUser, PutTeamDto putTeamDto, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeamId(authUser, teamId);

        // 2. 수정 후 저장
        team.update(putTeamDto);
        teamsRepository.save(team);
    }

    @Transactional
    public void deleteTeam(User authUser, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeamId(authUser, teamId);

        // 2. 팀원 삭제
        List<Teammate> teammates = teammateRepository.findAllByTeam(team);
        teammateRepository.deleteAll(teammates);

        // 3. 삭제
        teamsRepository.delete(team);
    }

    @Transactional
    public GetScheduleDto getSchedule(User authUser, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeamId(authUser, teamId);

        // 2. 스케줄 조회
        List<Schedule> schedules = scheduleRepository.findAllByTeamAndDueDateIsAfterOrderByDueDate(team, LocalDateTime.now());

        // 3. 스케줄 dto 생성
        List<ScheduleDto> scheduleDtoList = schedules.stream()
                .map(ScheduleDto::new)
                .collect(toList());

        return GetScheduleDto.builder()
                .name(team.getName())
                .dueDate(team.getDueDate())
                .schedules(scheduleDtoList)
                .build();
    }

    @Transactional
    public void postSchedule(User authUser, ScheduleDto scheduleDto, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeamId(authUser, teamId);

        // 2. 일정 생성
        Schedule schedule = Schedule.builder()
                .name(scheduleDto.getName())
                .dueDate(scheduleDto.getDueDate())
                .team(team)
                .build();
        scheduleRepository.save(schedule);
    }

    @Transactional
    public List<GetTeamTasksDto> getTeamTasks(User authUser, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeamId(authUser, teamId);

        // 2. 단계 불러오기
        List<Stage> stages = stagesRepository.findAllByTeamOrderBySequenceNum(team);

        // 3. 할 일 불러오기
        return stages.stream()
                .map(GetTeamTasksDto::new)
                .collect(toList());
    }

    @Transactional
    public GetTeammateDto getTeammate(User authUser, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeamId(authUser, teamId);

        // 2. 팀원 조회
        List<Teammate> teammates = teammateRepository.findAllByTeam(team);

        // 3. 팀메이트 dto 생성
        List<TeammateDto> teammateDtoList = teammates.stream()
                .filter(teammate -> !teammate.getUser().equals(authUser))
                .map(teammate ->
                         TeammateDto.builder()
                                .teammateId(teammate.getId())
                                .name(teammate.getUserProfile().getName())
                                .schoolName(teammate.getUserProfile().getSchoolName())
                                .major(teammate.getUserProfile().getMajor())
                                .build())
                .collect(toList());

        return GetTeammateDto.builder()
                .name(authUser.getUserProfile().getName())
                .schoolName(authUser.getUserProfile().getSchoolName())
                .major(authUser.getUserProfile().getMajor())
                .teammates(teammateDtoList)
                .build();
    }

    @Transactional
    public void deleteTeammate(User authUser, DeleteTeammateDto deleteTeammateDto, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeamId(authUser, teamId);

        // 2. 팀원 조회
        List<Teammate> teammates = teammateRepository.findAllByTeam(team);

        // 3. Teammate 삭제
        boolean isConducted = false;
        for (Teammate teammate : teammates) {
            if (teammate.getId().equals(deleteTeammateDto.getTeammateId())) {
                teammateRepository.delete(teammate);
                isConducted = true;
            }
        }
        if(!isConducted)
            throw new BadRequestException(ErrorCode.INVALID_TEAMMATE.getMessage());
    }

}
