package Backend.teampple.domain.stages;

import Backend.teampple.domain.stages.dto.StageDto;
import Backend.teampple.domain.stages.dto.request.PutStageDto;
import Backend.teampple.domain.teams.dto.request.PostTeamDto;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "단계")
@RequestMapping("/stages")
public class StagesController {

    private final StagesService stagesService;

    @GetMapping(value = "/{teamId}")
    @Operation(summary = "단계 조회", description = "단계 조회 API 입니다.\n"
            + "단계를 조회합니다.")
    public CommonResponse<List<StageDto>> getStage(@PathVariable Long teamId) {
        log.info("[api-get] 단계 조회");

        // 유저 validation 추가해야함

        List<StageDto> stageDtos = stagesService.getStage(teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), stageDtos);
    }

    @PostMapping(value = "/{teamId}")
    @Operation(summary = "단계 추가", description = "단계 추가 API 입니다.\n"
            + "단계를 추가합니다.")
    public CommonResponse<String> postStage(@Valid @RequestBody StageDto stageDto,
                                            @PathVariable Long teamId) {
        log.info("[api-post] 단계 추가");

        // 유저 validation 추가해야함

        stagesService.postStage(stageDto, teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }

    @PutMapping(value = "/{teamId}")
    @Operation(summary = "단계 수정", description = "단계 수정 API 입니다.\n"
            + "단계를 수정합니다.")
    public CommonResponse<String> putStage(@Valid @RequestBody PutStageDto putStageDto,
                                           @PathVariable Long teamId) {
        log.info("[api-post] 단계 추가");

        // 유저 validation 추가해야함

        stagesService.putStage(putStageDto.getStages(), teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }
}
