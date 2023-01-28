package Backend.teampple.domain.invitations;

import Backend.teampple.domain.invitations.dto.response.GetInvitationDto;
import Backend.teampple.domain.invitations.dto.response.GetInvitationValidationDto;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/invitations")
@Api(tags = "초대")
public class InvitationController {
    private final InvitationService invitationService;

    @GetMapping(value = "")
    @Operation(summary = "초대 링크 조회", description = "초대 링크 API 입니다.")
    public CommonResponse<GetInvitationDto> getInvitation(@AuthenticationPrincipal String authUser,
                                                          @RequestParam("teamId") Long teamId) {
        log.info("[api-get] 초대 링크 ");

        GetInvitationDto getInvitationDto = invitationService.getInvitation(authUser, teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), getInvitationDto);
    }

    @PostMapping(value = "")
    @Operation(summary = "유저 초대", description = "유저 초대 API 입니다.")
    public CommonResponse<String> postInvitation(@AuthenticationPrincipal String authUser,
                                                 @RequestParam("teamId") Long teamId) {
        log.info("[api-get] 초대 코드 검증 ");

        invitationService.postInvitation(authUser, teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value());
    }

    @GetMapping(value = "validation")
    @Operation(summary = "초대 코드 검증", description = "초대 코드 검증 API 입니다." +
            "토큰이 필요 없는 api 입니다. 유효하지 않은 코드일 경우 false 값을 보냅니다.")
    public CommonResponse<GetInvitationValidationDto> getInvitationValidation(@RequestParam("code") String code) {
        log.info("[api-get] 초대 코드 검증 ");

        GetInvitationValidationDto validationDto = invitationService.getInvitationValidation(code);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), validationDto);
    }
}
