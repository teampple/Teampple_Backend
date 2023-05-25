package Backend.teampple.domain.teams;

import Backend.teampple.domain.teams.dto.ScheduleDto;
import Backend.teampple.domain.teams.dto.request.*;
import Backend.teampple.domain.teams.dto.response.*;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.auth.AuthUser;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
@Api(tags = "팀플")
public class TeamsController {
    private final TeamsService teamsService;

    @GetMapping(value = "")
    @Operation(summary = "팀플 상세 정보 조회", description = "팀플 상세 정보 조회 API 입니다.")
    public GetTeamDetailDto getTeamDetail(@AuthUser User authUser,
                                                          @Valid @RequestParam("teamId") Long teamId) {
        log.info("[api-get] 팀 상세 정보 가져오기");
        log.info("{}", authUser);

        return teamsService.getTeamDetail(authUser, teamId);
    }

    @PostMapping(value = "")
    @Operation(summary = "팀플 생성", description = "팀플 생성 API 입니다.\n"
            + "stage가 없어도 []를 보내줘야 합니다.")
    public PostTeamResDto postTeam(@AuthUser User authUser,
                                                   @Valid @RequestBody PostTeamDto postTeamDto) {
        log.info("[api-post] 팀 생성");
        log.info("{}", authUser);

        return teamsService.createTeam(authUser, postTeamDto);
    }

    @PutMapping(value = "")
    @Operation(summary = "팀플 정보 수정", description = "팀플 정보 수정 API 입니다.")
    public void putTeam(@AuthUser User authUser,
                                          @Valid @RequestBody PutTeamDto putTeamDto,
                                          @RequestParam("teamId") Long teamId) {
        log.info("[api-put] 팀 정보 수정");
        log.info("{}", authUser);

        teamsService.putTeam(authUser, putTeamDto, teamId);
    }

    @DeleteMapping(value = "")
    @Operation(summary = "팀플 나가기", description = "팀플 삭제 API 입니다." +
            "팀원이 한 명일 경우, 팀플 삭제됩니다.")
    public void putTeam(@AuthUser User authUser,
                                          @RequestParam("teamId") Long teamId) {
        log.info("[api-delete] 팀 삭제");
        log.info("{}", authUser);

        teamsService.deleteTeam(authUser, teamId);
    }

    @PostMapping(value = "schedules")
    @Operation(summary = "팀플 일정 생성", description = "팀플 일정 생성 API 입니다.")
    public void postSchedule(@AuthUser User authUser,
                                               @Valid @RequestBody ScheduleDto scheduleDto,
                                               @RequestParam("teamId") Long teamId) {
        log.info("[api-post] 팀플 일정 생성");
        log.info("{}", authUser);

        teamsService.postSchedule(authUser, scheduleDto, teamId);
    }

    @GetMapping(value = "schedules")
    @Operation(summary = "팀플 일정 조회", description = "팀플 일정 조회 API 입니다."
            + "name 과 duedate는 팀의 이름과 마감일이고, schedule은 팀의 일정입니다.")
    public GetScheduleDto getSchedule(@AuthUser User authUser,
                                      @RequestParam("teamId") Long teamId) {
        log.info("[api-post] 팀플 일정 생성");
        log.info("{}", authUser);

        return teamsService.getSchedule(authUser, teamId);
    }

    @GetMapping(value = "tasks")
    @Operation(summary = "팀플 할 일 정보 조회", description = "팀플 할 일 정보 조회 API 입니다.")
    public List<GetTeamTasksDto> getTeamTasks(@AuthUser User authUser,
                                              @RequestParam("teamId") Long teamId) {
        log.info("[api-get] 팀 할 일 정보 조회");
        log.info("{}", authUser);

        return teamsService.getTeamTasks(authUser, teamId);
    }

    @GetMapping(value = "teammates")
    @Operation(summary = "팀원 정보 조회", description = "팀원 정보 조회 API 입니다."
            + "리스트가 아닌 값들은 '나'의 정보이고, 리스트는 '나'를 제외한 팀원입니다.")
    public GetTeammateDto getTeammate(@AuthUser User authUser,
                                      @RequestParam("teamId") Long teamId) {
        log.info("[api-get] 팀원 정보 조회");
        log.info("{}", authUser);

        return teamsService.getTeammate(authUser, teamId);
    }

    @DeleteMapping(value = "teammates")
    @Operation(summary = "팀원 삭제", description = "팀원 삭제 API 입니다.")
    public void deleteTeammate(@AuthUser User authUser,
                               @Valid @RequestBody DeleteTeammateDto deleteTeammateDto,
                               @RequestParam("teamId") Long teamId) {
        log.info("[api-delete] 팀원 삭제");
        log.info("{}", authUser);

        teamsService.deleteTeammate(authUser, deleteTeammateDto, teamId);
    }

}
