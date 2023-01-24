package Backend.teampple.domain.teams;

import Backend.teampple.domain.stages.repository.StagesRepository;
import Backend.teampple.domain.stages.dto.StageDto;
import Backend.teampple.domain.stages.entity.Stage;
import Backend.teampple.domain.teams.dto.ScheduleDto;
import Backend.teampple.domain.teams.dto.TeammateDto;
import Backend.teampple.global.common.validation.dto.UserTeamDto;
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
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.global.common.validation.CheckUser;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.BadRequestException;
import Backend.teampple.global.error.exception.NotFoundException;
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

    private final UserRepository userRepository;

    private final CheckUser checkUser;

    @Transactional
    public GetTeamDetailDto getTeamDetail(String authUser, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeam(authUser, teamId).getTeam();

        // 2. 팀원 조회
        List<Teammate> teammates = teammateRepository.findAllByTeam(team);

        // 3. 팀원 관련 정보 변환
        List<String> teammatesImages = new ArrayList<>();
        teammates.forEach(teammate -> {
            teammatesImages.add(teammate.getUserProfile().getProfileImage());
        });

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
    public void createTeam(String authUser, PostTeamDto postTeamDto) {
        // 1. 팀 생성
        Team team = Team.builder()
                .name(postTeamDto.getName())
                .goal(postTeamDto.getGoal())
                .startDate(postTeamDto.getStartDate())
                .dueDate(postTeamDto.getDueDate())
                .build();
        teamsRepository.save(team);

        // 2. 팀 생성한 사람 팀원으로 추가
        User user = userRepository.findByKakaoIdWithUserProfile(authUser)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));
        Teammate teammate = Teammate.builder()
                .team(team)
                .user(user)
                .userProfile(user.getUserProfile())
                .build();
        teammateRepository.save(teammate);

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
    public void putTeam(String authUser, PutTeamDto putTeamDto, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeam(authUser, teamId).getTeam();

        // 2. 수정 후 저장
        team.update(putTeamDto);
        teamsRepository.save(team);
    }

    @Transactional
    public GetScheduleDto getSchedule(String authUser, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeam(authUser, teamId).getTeam();

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

        return GetScheduleDto.builder()
                .name(team.getName())
                .dueDate(team.getDueDate())
                .schedules(scheduleDtoList)
                .build();
    }

    @Transactional
    public void postSchedule(String authUser, ScheduleDto scheduleDto, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeam(authUser, teamId).getTeam();

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
    public List<GetTeamTasksDto> getTeamTasks(String authUser, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        Team team = checkUser.checkIsUserInTeam(authUser, teamId).getTeam();

        // 2. 단계 불러오기
        List<Stage> stages = stagesRepository.findAllByTeamOrderBySequenceNum(team);

        // 3. 할 일 불러오기
        return stages.stream()
                .map(GetTeamTasksDto::new)
                .collect(toList());
    }

    @Transactional
    public GetTeammateDto getTeammate(String authUser, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        UserTeamDto userTeamDto = checkUser.checkIsUserInTeam(authUser, teamId);

        // 2. 팀원 조회
        List<Teammate> teammates = teammateRepository.findAllByTeam(userTeamDto.getTeam());

        // 3. 팀메이트 dto 생성
        User user = userTeamDto.getUser();
        List<TeammateDto> teammateDtoList = new ArrayList<>();
        teammates.forEach(teammate -> {
            if (!Objects.equals(teammate.getId(), user.getId())) {
                TeammateDto converted = TeammateDto.builder()
                        .teammateId(teammate.getId())
                        .name(teammate.getUserProfile().getName())
                        .schoolName(teammate.getUserProfile().getSchoolName())
                        .major(teammate.getUserProfile().getMajor())
                        .build();
                teammateDtoList.add(converted);

            }
        });

        return GetTeammateDto.builder()
                .name(user.getUserProfile().getName())
                .schoolName(user.getUserProfile().getSchoolName())
                .major(user.getUserProfile().getMajor())
                .teammates(teammateDtoList)
                .build();
    }

    @Transactional
    public void deleteTeammate(String authUser, DeleteTeammateDto deleteTeammateDto, Long teamId) {
        // 1. 유저 체크 및 team 정보 불러오기
        UserTeamDto userTeamDto = checkUser.checkIsUserInTeam(authUser, teamId);

        // 2. 팀원 조회
        List<Teammate> teammates = teammateRepository.findAllByTeam(userTeamDto.getTeam());

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
