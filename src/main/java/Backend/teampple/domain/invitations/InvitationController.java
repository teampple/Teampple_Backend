package Backend.teampple.domain.invitations;

import Backend.teampple.domain.invitations.dto.response.GetInvitationDto;
import Backend.teampple.domain.invitations.dto.response.GetInvitationValidationDto;
import Backend.teampple.domain.tasks.dto.response.GetTaskDto;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/invitations")
@Api(tags = "초대")
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping(value = "")
    @Operation(summary = "초대 링크 조회", description = "초대 링크 API 입니다.\n"
            + "초대 링크를 조회합니다.")
    public CommonResponse<GetInvitationDto> getInvitation(@RequestParam("teamId") Long teamId) {
        log.info("[api-get] 초대 링크 ");

        // 유저 validation 추가해야함

        GetInvitationDto getInvitationDto = invitationService.getInvitation(teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), getInvitationDto);
    }

    @GetMapping(value = "/validation")
    @Operation(summary = "초대 코드 검증", description = "초대 코드 검증 API 입니다.\n"
            + "초대 코드를 검증합니다.")
    public CommonResponse<GetInvitationValidationDto> getInvitationValidation(@RequestParam("code") String code) {
        log.info("[api-get] 초대 코드 검증 ");

        // 유저 validation 추가해야함

        GetInvitationValidationDto validationDto = invitationService.getInvitationValidation(code);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), validationDto);
    }

    @PostMapping(value = "")
    @Operation(summary = "유저 초대", description = "유저 초대 API 입니다.\n"
            + "유저를 초대합니다.")
    public CommonResponse<String> postInvitation(@RequestParam("teamId") Long teamId) {
        log.info("[api-get] 초대 코드 검증 ");

        // 유저 validation 추가해야함

        invitationService.postInvitation(teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }
}
