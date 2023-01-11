package Backend.teampple.domain.auth;

import Backend.teampple.domain.users.dto.request.PostUserProfileDto;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.domain.users.service.UserProfileService;
import Backend.teampple.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService{
    private final UserService userService;
    private final UserProfileService userProfileService;

    @Override
    public void logout(String refreshToken) {
        userService.deleteUserRefreshToken(refreshToken);
    }

    public void signUp(PostUserProfileDto postUserProfileDto){
        UserProfile profile = userProfileService.createProfile(postUserProfileDto);
        userService.createUser(profile);
    }

    public void withdrawal(String refreshToken) {
        userService.deleteUser(refreshToken);
    }
}
