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
    public CommonResponse<GetTeamDetailDto> getTeamDetail(@AuthUser User authUser,
                                                          @Valid @RequestParam("teamId") Long teamId) {
        log.info("[api-get] 팀 상세 정보 가져오기");
        log.info("{}", authUser);

        GetTeamDetailDto teamDetail = teamsService.getTeamDetail(authUser, teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), teamDetail);
    }

    @PostMapping(value = "")
    @Operation(summary = "팀플 생성", description = "팀플 생성 API 입니다.\n"
            + "stage가 없어도 []를 보내줘야 합니다.")
    public CommonResponse<PostTeamResDto> postTeam(@AuthUser User authUser,
                                                   @Valid @RequestBody PostTeamDto postTeamDto) {
        log.info("[api-post] 팀 생성");
        log.info("{}", authUser);

        PostTeamResDto postTeamResDto = teamsService.createTeam(authUser, postTeamDto);
        return CommonResponse.onSuccess(HttpStatus.CREATED.value(), postTeamResDto);
    }

    @PutMapping(value = "")
    @Operation(summary = "팀플 정보 수정", description = "팀플 정보 수정 API 입니다.")
    public CommonResponse<String> putTeam(@AuthUser User authUser,
                                          @Valid @RequestBody PutTeamDto putTeamDto,
                                          @RequestParam("teamId") Long teamId) {
        log.info("[api-put] 팀 정보 수정");
        log.info("{}", authUser);

        teamsService.putTeam(authUser, putTeamDto, teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }

    @PostMapping(value = "schedules")
    @Operation(summary = "팀플 일정 생성", description = "팀플 일정 생성 API 입니다.")
    public CommonResponse<String> postSchedule(@AuthUser User authUser,
                                               @Valid @RequestBody ScheduleDto scheduleDto,
                                               @RequestParam("teamId") Long teamId) {
        log.info("[api-post] 팀플 일정 생성");
        log.info("{}", authUser);

        teamsService.postSchedule(authUser, scheduleDto, teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }

    @GetMapping(value = "schedules")
    @Operation(summary = "팀플 일정 조회", description = "팀플 일정 조회 API 입니다."
            + "name 과 duedate는 팀의 이름과 마감일이고, schedule은 팀의 일정입니다.")
    public CommonResponse<GetScheduleDto> getSchedule(@AuthUser User authUser,
                                                      @RequestParam("teamId") Long teamId) {
        log.info("[api-post] 팀플 일정 생성");
        log.info("{}", authUser);

        GetScheduleDto schedules = teamsService.getSchedule(authUser, teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), schedules);
    }

    @GetMapping(value = "tasks")
    @Operation(summary = "팀플 할 일 정보 조회", description = "팀플 할 일 정보 조회 API 입니다.")
    public CommonResponse<List<GetTeamTasksDto>> getTeamTasks(@AuthUser User authUser,
                                                              @RequestParam("teamId") Long teamId) {
        log.info("[api-get] 팀 할 일 정보 조회");
        log.info("{}", authUser);

        List<GetTeamTasksDto> teamTasks = teamsService.getTeamTasks(authUser, teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), teamTasks);
    }

    @GetMapping(value = "teammates")
    @Operation(summary = "팀원 정보 조회", description = "팀원 정보 조회 API 입니다."
            + "리스트가 아닌 값들은 '나'의 정보이고, 리스트는 '나'를 제외한 팀원입니다.")
    public CommonResponse<GetTeammateDto> getTeammate(@AuthUser User authUser,
                                                      @RequestParam("teamId") Long teamId) {
        log.info("[api-get] 팀원 정보 조회");
        log.info("{}", authUser);

        GetTeammateDto schedules = teamsService.getTeammate(authUser, teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), schedules);
    }

    @DeleteMapping(value = "teammates")
    @Operation(summary = "팀원 삭제", description = "팀원 삭제 API 입니다.")
    public CommonResponse<String> deleteTeammate(@AuthUser User authUser,
                                                 @Valid @RequestBody DeleteTeammateDto deleteTeammateDto,
                                                 @RequestParam("teamId") Long teamId) {
        log.info("[api-delete] 팀원 삭제");
        log.info("{}", authUser);

        teamsService.deleteTeammate(authUser, deleteTeammateDto, teamId);
        return CommonResponse.onSuccess(HttpStatus.NO_CONTENT.value());
    }

}
