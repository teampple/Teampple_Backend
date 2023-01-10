package Backend.teampple.domain.users;

import Backend.teampple.domain.users.dto.request.PostUserProfileDto;
import Backend.teampple.domain.users.dto.request.PutUserProfileDto;
import Backend.teampple.domain.users.dto.response.GetUserProfileDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
@Api(tags = "사용자")
public class UserController {
    private final UserService userService;

    @PostMapping(value = "")
    public GetUserProfileDto signUp(@Valid @RequestBody PostUserProfileDto postUserProfileDto) {
        return userService.signUp(postUserProfileDto);
    }

    @GetMapping("userprofiles")
    public GetUserProfileDto getProfiles(Long id) {
        return userService.getUserProfile(id);
    }

    @PutMapping("userprofiles")
    public GetUserProfileDto updateProfile(Long id, @RequestBody PutUserProfileDto putUserProfileDto) {
        return userService.updateUserProfile(id, putUserProfileDto);
    }

}
