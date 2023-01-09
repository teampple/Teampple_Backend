package Backend.teampple.domain.users;

import Backend.teampple.domain.users.dto.UserProfileDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@Api(tags = "사용자")
public class UserController {
    private final UserService userService;

    @PostMapping(value = "")
    public UserProfileDto signUp(@Valid @RequestBody UserProfileDto userProfileDto){
        return userService.signUp(userProfileDto);
    }

    @GetMapping("userprofiles")
    public UserProfileDto getProfiles(Long id) {
        return userService.getUserProfile(id);
    }

    @PutMapping("userprofiles")
    public UserProfileDto updateProfile(Long id, @RequestBody UserProfileDto userProfileDto) {
        return userService.updateUserProfile(id, userProfileDto);
    }

}
