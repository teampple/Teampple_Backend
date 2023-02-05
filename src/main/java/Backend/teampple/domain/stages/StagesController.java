package Backend.teampple.domain.stages;

import Backend.teampple.domain.stages.dto.StageDto;
import Backend.teampple.domain.stages.dto.request.PostStageDto;
import Backend.teampple.domain.stages.dto.request.PutStageDto;
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
@RequestMapping("/stages")
@Api(tags = "단계")
public class StagesController {
    private final StagesService stagesService;

    @GetMapping(value = "")
    @Operation(summary = "단계 조회", description = "단계 조회 API 입니다.")
    public CommonResponse<List<StageDto>> getStage(@AuthUser User authUser,
                                                   @RequestParam("teamId") Long teamId) {
        log.info("[api-get] 단계 조회");
        log.info("{}", authUser);

        List<StageDto> stageDtos = stagesService.getStage(authUser, teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), stageDtos);
    }

    @PostMapping(value = "")
    @Operation(summary = "단계 추가", description = "단계 추가 API 입니다.")
    public CommonResponse<String> postStage(@AuthUser User authUser,
                                            @Valid @RequestBody PostStageDto postStageDto,
                                            @RequestParam("teamId") Long teamId) {
        log.info("[api-post] 단계 추가");
        log.info("{}", authUser);

        stagesService.postStage(authUser, postStageDto, teamId);
        return CommonResponse.onSuccess(HttpStatus.CREATED.value());
    }

    @PutMapping(value = "")
    @Operation(summary = "단계 수정", description = "단계 수정 API 입니다.")
    public CommonResponse<String> putStage(@AuthUser User authUser,
                                           @Valid @RequestBody PutStageDto putStageDto,
                                           @RequestParam("teamId") Long teamId) {
        log.info("[api-put] 단계 수정");
        log.info("{}", authUser);

        stagesService.putStage(authUser, putStageDto, teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }
}
