package Backend.teampple.domain.teams;

import Backend.teampple.domain.teams.dto.request.PostTeamDto;
import Backend.teampple.domain.teams.dto.response.GetTeamDetailDto;
import Backend.teampple.global.common.response.CommonResponse;
import Backend.teampple.global.error.exception.BadRequestException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
@Slf4j
@Api(tags = "팀플")
public class TeamsController {
    private final TeamsService teamsService;

    @PostMapping(value = "")
    @Operation(summary = "팀플 생성", description = "팀플 생성 API 입니다.\n"
            + "팀과 팀의 단계를 생성합니다. 팀 생성시 팀을 생성한 유저가 팀의 팀원으로 추가됩니다.")
    public CommonResponse<String> postTeam(@Valid @RequestBody PostTeamDto postTeamDto) {
        log.info("[api-post] 팀 생성");

        // 유저 validation 추가해야함

        teamsService.createTeam(postTeamDto);
        return CommonResponse.onSuccess(HttpStatus.CREATED.value());
    }

    @GetMapping(value = "/{teamId}")
    @Operation(summary = "팀플 상세 정보 조회", description = "팀플 상세 정보 조회 API 입니다.\n"
            + "팀플 화면 헤더에 쓰일 정보를 조회합니다. stage가 없어도 []를 보내줘야 합니다.")
    public CommonResponse<GetTeamDetailDto> getTeamDetail(@PathVariable Long teamId) {
        log.info("[api-get] 팀 상세 정보 가져오기");

        // 유저 validation 추가해야함

        GetTeamDetailDto teamDetail = teamsService.getTeamDetail(teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), teamDetail);
    }

}
