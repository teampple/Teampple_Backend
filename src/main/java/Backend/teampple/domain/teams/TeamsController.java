package Backend.teampple.domain.teams;

import Backend.teampple.domain.teams.dto.request.PostTeamDto;
import Backend.teampple.domain.teams.dto.response.GetTeamDetailDto;
import Backend.teampple.global.common.response.CommonResponse;
import Backend.teampple.global.error.exception.BadRequestException;
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
public class TeamsController {
    private final TeamsService teamsService;

    @PostMapping(value = "")
    public CommonResponse<String> postTeam(@Valid @RequestBody PostTeamDto postTeamDto) {
        log.info("[api-post] 팀 생성");

        // 유저 validation 추가해야함

        teamsService.createTeam(postTeamDto);
        return CommonResponse.onSuccess(HttpStatus.CREATED.value());
    }

    @GetMapping(value = "/{teamId}")
    public CommonResponse<GetTeamDetailDto> getTeamDetail(@PathVariable Long teamId) {
        log.info("[api-get] 팀 상세 정보 가져오기");

        // 유저 validation 추가해야함

        GetTeamDetailDto teamDetail = teamsService.getTeamDetail(teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), teamDetail);
    }

}
