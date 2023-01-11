package Backend.teampple.domain.users.service;

import Backend.teampple.domain.users.dto.request.PostUserProfileDto;
import Backend.teampple.domain.users.dto.request.PutUserProfileDto;
import Backend.teampple.domain.users.dto.response.GetUserProfileDto;
import Backend.teampple.domain.users.entity.UserProfile;
import org.springframework.stereotype.Service;

@Service
public interface UserProfileService {
    UserProfile createProfile(PostUserProfileDto postUserProfileDto);

    GetUserProfileDto getUserProfile(String refreshToken);

    GetUserProfileDto updateUserProfile(String refreshToken, PutUserProfileDto putUserProfileDto);

    void deleteUserProfile(UserProfile userProfile);
}
