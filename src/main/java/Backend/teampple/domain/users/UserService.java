package Backend.teampple.domain.users;

import Backend.teampple.domain.users.dto.request.PostUserProfileDto;
import Backend.teampple.domain.users.dto.request.PutUserProfileDto;
import Backend.teampple.domain.users.dto.response.GetUserProfileDto;

public interface UserService {
    GetUserProfileDto signUp(PostUserProfileDto postUserProfileDto);

    GetUserProfileDto getUserProfile(Long userId);

    GetUserProfileDto updateUserProfile(Long userId, PutUserProfileDto putUserProfileDto);

    String getTasks(Long userId);

    String getFeedbacks(Long userId);
}
