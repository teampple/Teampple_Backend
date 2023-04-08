package Backend.teampple.domain.users;

import Backend.teampple.domain.users.dto.request.PutUserProfileDto;
import Backend.teampple.domain.users.dto.response.GetUserFeedbacksDto;
import Backend.teampple.domain.users.dto.response.GetUserProfileDto;
import Backend.teampple.domain.users.dto.response.GetUserTasksDto;
import Backend.teampple.domain.users.dto.response.GetUserTeamsDto;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.service.UserProfileService;
import Backend.teampple.domain.users.service.UserService;
import Backend.teampple.global.common.auth.AuthUser;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@Api(tags = "사용자")
public class UserController {
    private final UserProfileService userProfileService;
    private final UserService userService;

    @GetMapping("/userprofiles")
    @Operation(summary = "프로필 요청", description = "프로필 요청 API 입니다.")
    public GetUserProfileDto getProfiles(@AuthUser User user) {
        return userProfileService.getUserProfile(user);
    }

    @PutMapping("/userprofiles")
    @Operation(summary = "프로필 수정 요청", description = "프로필 수정 API 입니다.")
    public GetUserProfileDto updateProfile(@AuthUser User user, @RequestBody PutUserProfileDto putUserProfileDto) {
        return userProfileService.updateUserProfile(user, putUserProfileDto);
    }

    @GetMapping(value = "tasks")
    @Operation(summary = "유저 할 일 정보 조회", description = "유저 할 일 정보 조회 API 입니다.")
    public GetUserTasksDto getUserTasks(@AuthUser User authUser) {
        log.info("[api-get] 유저 할 일 정보 조회");
        log.info("{}", authUser);

        return userService.getUserTasks(authUser);
    }

    @GetMapping(value = "teams")
    @Operation(summary = "유저 팀플 정보 조회", description = "유저 팀플 정보 조회 API 입니다.")
    public GetUserTeamsDto getUserTeams(@AuthUser User authUser,
                                                        @RequestParam("active") boolean isActive ) {
        log.info("[api-get] 유저 팀플 정보 조회");
        log.info("{}", authUser);

        return userService.getUserTeams(authUser, isActive);
    }

    @GetMapping(value = "feedbacks")
    @Operation(summary = "유저 피드백 정보 조회", description = "팀플 피드백 정보 조회 API 입니다.")
    public GetUserFeedbacksDto getUserFeedbacks(@AuthUser User authUser) {
        log.info("[api-get] 유저 피드백 정보 조회");
        log.info("{}", authUser);

        return userService.getUserFeedbacks(authUser);
    }
}
