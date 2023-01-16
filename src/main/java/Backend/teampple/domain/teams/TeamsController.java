package Backend.teampple.domain.teams;

import Backend.teampple.domain.teams.dto.ScheduleDto;
import Backend.teampple.domain.teams.dto.request.*;
import Backend.teampple.domain.teams.dto.response.GetScheduleDto;
import Backend.teampple.domain.teams.dto.response.GetTeamDetailDto;
import Backend.teampple.domain.teams.dto.response.GetTeamTasksDto;
import Backend.teampple.domain.teams.dto.response.GetTeammateDto;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @PostMapping(value = "")
    @Operation(summary = "팀플 생성", description = "팀플 생성 API 입니다.\n"
            + "팀과 팀의 단계를 생성합니다. stage가 없어도 []를 보내줘야 합니다.")
    public CommonResponse<String> postTeam(@Valid @RequestBody PostTeamDto postTeamDto) {
        log.info("[api-post] 팀 생성");

        // 팀 생성시 팀을 생성한 유저가 팀의 팀원으로 추가되도록 수정
        // 유저 validation 추가해야함

        teamsService.createTeam(postTeamDto);
        return CommonResponse.onSuccess(HttpStatus.CREATED.value());
    }

    @GetMapping(value = "")
    @Operation(summary = "팀플 상세 정보 조회", description = "팀플 상세 정보 조회 API 입니다.\n"
            + "팀플 화면 헤더에 쓰일 정보를 조회합니다.")
    public CommonResponse<GetTeamDetailDto> getTeamDetail(@RequestParam("teamId") Long teamId) {
        log.info("[api-get] 팀 상세 정보 가져오기");

        // 유저 validation 추가해야함

        GetTeamDetailDto teamDetail = teamsService.getTeamDetail(teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), teamDetail);
    }

    @GetMapping(value = "/tasks")
    @Operation(summary = "팀플 할 일 정보 조회", description = "팀플 할 일 정보 조회 API 입니다.\n"
            + "팀플 화면에 쓰일 정보를 조회합니다.")
    public CommonResponse<List<GetTeamTasksDto>> getTeamTasks(@RequestParam("teamId") Long teamId) {
        log.info("[api-get] 팀 할 일 정보 조회");

        // 유저 validation 추가해야함

        List<GetTeamTasksDto> teamTasks = teamsService.getTeamTasks(teamId);

        return CommonResponse.onSuccess(HttpStatus.OK.value(), teamTasks);
    }

    @PutMapping(value = "")
    @Operation(summary = "팀플 정보 수정", description = "팀플 정보 수정 API 입니다.\n"
            + "팀플 정보를 수정합니다.")
    public CommonResponse<String> putTeam(@Valid @RequestBody PutTeamDto putTeamDto,
                                          @RequestParam("teamId") Long teamId) {
        log.info("[api-put] 팀 정보 수정");

        // 유저 validation 추가해야함

        teamsService.putTeam(putTeamDto, teamId);

        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }

    @PostMapping(value = "/schedules")
    @Operation(summary = "팀플 일정 생성", description = "팀플 일정 생성 API 입니다.\n"
            + "팀플 일정을 생성합니다.")
    public CommonResponse<String> postSchedule(@Valid @RequestBody ScheduleDto scheduleDto,
                                               @RequestParam("teamId") Long teamId) {
        log.info("[api-post] 팀플 일정 생성");

        // 유저 validation 추가해야함

        teamsService.postSchedule(scheduleDto, teamId);

        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }

    @GetMapping(value = "/schedules")
    @Operation(summary = "팀플 일정 조회", description = "팀플 일정 조회 API 입니다.\n"
            + "팀플 일정을 조회합니다."
            + "name 과 duedate는 팀의 이름과 마감일이고, schedule은 팀의 일정입니다.")
    public CommonResponse<GetScheduleDto> getSchedule(@RequestParam("teamId") Long teamId) {
        log.info("[api-post] 팀플 일정 생성");

        // 유저 validation 추가해야함

        GetScheduleDto schedules = teamsService.getSchedule(teamId);

        return CommonResponse.onSuccess(HttpStatus.OK.value(), schedules);
    }

    @PostMapping(value = "/teammates")
    @Operation(summary = "팀원 추가", description = "팀원 추가 API 입니다.\n"
            + "팀원을 추가합니다.")
    public CommonResponse<String> postTeammate(@Valid @RequestBody PostTeammateDto postTeammateDto) {
        log.info("[api-post] 팀원 추가");

        // 유저 validation 추가해야함

        teamsService.postTeammate(postTeammateDto);

        return CommonResponse.onSuccess(HttpStatus.NO_CONTENT.value());
    }

    @GetMapping(value = "/teammates")
    @Operation(summary = "팀원 정보 조회", description = "팀원 정보 조회 API 입니다.\n"
            + "팀원 정보를 조회합니다."
            + "리스트가 아닌 값들은 '나'의 정보이고, 리스트는 '나'를 제외한 팀원입니다.")
    public CommonResponse<GetTeammateDto> getTeammate(@RequestParam("teamId") Long teamId) {
        log.info("[api-get] 팀원 정보 조회");

        // 유저 validation 추가해야함

        GetTeammateDto schedules = teamsService.getTeammate(teamId);

        return CommonResponse.onSuccess(HttpStatus.OK.value(), schedules);
    }

    @DeleteMapping(value = "/teammates")
    @Operation(summary = "팀원 삭제", description = "팀원 삭제 API 입니다.\n"
            + "팀원을 삭제합니다.")
    public CommonResponse<String> deleteTeammate(@Valid @RequestBody DeleteTeammateDto deleteTeammateDto,
                                                 @RequestParam("teamId") Long teamId) {
        log.info("[api-delete] 팀원 삭제");

        // 유저 validation 추가해야함

        teamsService.deleteTeammate(deleteTeammateDto, teamId);

        return CommonResponse.onSuccess(HttpStatus.NO_CONTENT.value());
    }

}
