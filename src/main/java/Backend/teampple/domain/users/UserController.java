package Backend.teampple.domain.users;

import Backend.teampple.domain.users.dto.request.PutUserProfileDto;
import Backend.teampple.domain.users.dto.response.GetUserProfileDto;
import Backend.teampple.domain.users.service.UserProfileService;
import Backend.teampple.global.common.response.CommonResponse;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@Api(tags = "사용자")
public class UserController {
    private final UserProfileService userProfileService;

    @GetMapping("/userprofiles")
    public CommonResponse<GetUserProfileDto> getProfiles(@RequestBody String refreshToken) {
        GetUserProfileDto userProfile = userProfileService.getUserProfile(refreshToken);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), userProfile);
    }

    @PutMapping("/userprofiles")
    public CommonResponse<GetUserProfileDto> updateProfile(@RequestBody String refreshToken, @RequestBody PutUserProfileDto putUserProfileDto) {
        GetUserProfileDto userProfile = userProfileService.updateUserProfile(refreshToken, putUserProfileDto);
        return CommonResponse.onSuccess(HttpStatus.OK.value(), userProfile);
    }
}
