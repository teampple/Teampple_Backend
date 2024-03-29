package Backend.teampple.domain.invitations;

import Backend.teampple.domain.invitations.dto.response.GetInvitationDto;
import Backend.teampple.domain.invitations.dto.response.GetInvitationValidationDto;
import Backend.teampple.domain.invitations.dto.response.PostInvitationDto;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.global.common.auth.AuthUser;
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
    @Operation(summary = "초대 링크 조회", description = "초대 링크 API 입니다." +
            "스웨거 테스트시 referer, host 입력 안해도 됩니다.")
    public GetInvitationDto getInvitation(@AuthUser User authUser,
                                                          @RequestParam("teamId") Long teamId,
                                                          @RequestHeader(value = "referer", required = false) String referer,
                                                          @RequestHeader(value = "host", required = false) String host) {
        log.info("[api-get] 초대 링크 ");
        log.info("{}", authUser);

        return invitationService.getInvitation(authUser, teamId, referer, host);
    }

    @PostMapping(value = "")
    @Operation(summary = "유저 초대", description = "유저 초대 API 입니다.")
    public PostInvitationDto postInvitation(@AuthUser User authUser,
                                                            @RequestParam("code") String code) {
        log.info("[api-post] 유저 초대");
        log.info("{}", authUser);

        return invitationService.postInvitation(authUser, code);
    }

    @GetMapping(value = "validation")
    @Operation(summary = "초대 코드 검증", description = "초대 코드 검증 API 입니다." +
            "토큰이 필요 없는 api 입니다. 유효하지 않은 코드일 경우 false 값을 보냅니다.")
    public GetInvitationValidationDto getInvitationValidation(@RequestParam("code") String code) {
        log.info("[api-get] 초대 코드 검증 ");

        return invitationService.getInvitationValidation(code);
    }
}
