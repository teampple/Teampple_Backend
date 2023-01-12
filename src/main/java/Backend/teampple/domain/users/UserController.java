package Backend.teampple.domain.users;

import Backend.teampple.domain.users.dto.request.PutUserProfileDto;
import Backend.teampple.domain.users.dto.response.GetUserProfileDto;
import Backend.teampple.domain.users.service.UserProfileService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@Api(tags = "사용자")
public class UserController {
    private final UserProfileService userProfileService;

    @GetMapping("userprofiles")
    public GetUserProfileDto getProfiles(String refreshToken) {
        return userProfileService.getUserProfile(refreshToken);
    }

    @PutMapping("userprofiles")
    public GetUserProfileDto updateProfile(String refreshToken, @RequestBody PutUserProfileDto putUserProfileDto) {
        return userProfileService.updateUserProfile(refreshToken, putUserProfileDto);
    }
}
