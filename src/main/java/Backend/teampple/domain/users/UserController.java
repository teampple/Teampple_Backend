package Backend.teampple.domain.users;

import Backend.teampple.domain.users.dto.request.PutUserProfileDto;
import Backend.teampple.domain.users.dto.response.GetUserProfileDto;
import Backend.teampple.domain.users.service.UserProfileService;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@Api(tags = "사용자")
public class UserController {
    private final UserProfileService userProfileService;

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
}
