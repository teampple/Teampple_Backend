package Backend.teampple.domain.users.service;

import Backend.teampple.domain.users.dto.response.GetUserTasksDto;
import Backend.teampple.domain.users.entity.UserProfile;


public interface UserService {
    void createUser(UserProfile userProfile, String kakaoId, String refreshToken);

    void updateUserRefreshToken(String kakaoId, String refreshToken);

    void deleteUserRefreshToken(String kakaoId);

    void deleteUser(String kakaoId);

    GetUserTasksDto getUserTasks(String authUser, Long teamId);
}
