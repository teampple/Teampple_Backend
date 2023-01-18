package Backend.teampple.domain.users.service;

import Backend.teampple.domain.users.dto.request.PutUserProfileDto;
import Backend.teampple.domain.users.dto.response.GetUserProfileDto;
import Backend.teampple.domain.users.entity.UserProfile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface UserProfileService {
    UserProfile createProfile(UserProfile userProfile);

    GetUserProfileDto getUserProfile(Authentication authentication);

    GetUserProfileDto updateUserProfile(Authentication authentication, PutUserProfileDto putUserProfileDto);

    void deleteUserProfile(UserProfile userProfile);
}
