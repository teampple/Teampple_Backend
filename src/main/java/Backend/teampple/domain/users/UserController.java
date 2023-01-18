package Backend.teampple.domain.users;

import Backend.teampple.domain.teams.dto.response.GetTeamTasksDto;
import Backend.teampple.domain.users.dto.request.PutUserProfileDto;
import Backend.teampple.domain.users.dto.response.GetUserProfileDto;
import Backend.teampple.domain.users.dto.response.GetUserTasksDto;
import Backend.teampple.domain.users.service.UserProfileService;
import Backend.teampple.domain.users.service.UserService;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@Api(tags = "사용자")
public class UserController {
    private final UserProfileService userProfileService;

    private final UserService userService;

    @ApiOperation(value = "프로필 요청")
    @GetMapping("/userprofiles")
    public CommonResponse<GetUserProfileDto> getProfiles(Authentication authentication) {
        GetUserProfileDto userProfile = userProfileService.getUserProfile(authentication);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), userProfile);
    }

    @ApiOperation(value = "프로필 수정 요청")
    @PutMapping("/userprofiles")
    public CommonResponse<GetUserProfileDto> updateProfile(Authentication authentication, @RequestBody PutUserProfileDto putUserProfileDto) {
        GetUserProfileDto userProfile = userProfileService.updateUserProfile(authentication, putUserProfileDto);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), userProfile);
    }

    @GetMapping(value = "tasks")
    @Operation(summary = "팀플 할 일 정보 조회", description = "팀플 할 일 정보 조회 API 입니다.\n"
            + "팀플 화면에 쓰일 정보를 조회합니다.")
    public CommonResponse<GetUserTasksDto> getTeamTasks(@AuthenticationPrincipal String authUser,
                                                              @RequestParam("teamId") Long teamId) {
        log.info("[api-get] 팀 할 일 정보 조회");

        GetUserTasksDto getUserTasksDto = userService.getUserTasks(authUser, teamId);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), getUserTasksDto);
    }
}
