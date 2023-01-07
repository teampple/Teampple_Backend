package Backend.teampple.domain.users;

import Backend.teampple.domain.users.dto.UserProfileDto;

public interface UserService {
    UserProfileDto getUserProfile(Long userId);

    UserProfileDto updateUserProfile(Long userId, UserProfileDto dto);

    String getTasks(Long userId);

    String getFeedbacks(Long userId);
}
