package Backend.teampple.domain.users.service;

import Backend.teampple.domain.users.dto.response.GetUserFeedbacksDto;
import Backend.teampple.domain.users.dto.response.GetUserTasksDto;
import Backend.teampple.domain.users.dto.response.GetUserTeamsDto;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.entity.UserProfile;

import java.util.Date;


public interface UserService {
    void saveUserProfile(UserProfile userProfile, String kakaoId);

    void updateUserRefreshToken(String kakaoId, String refreshToken, Date expRT);

    void deleteUserRefreshToken(User user);

    void deleteUser(String kakaoId);

    GetUserTasksDto getUserTasks(String authUser);

    GetUserTeamsDto getUserTeams(String authUser, boolean isActive);

    GetUserFeedbacksDto getUserFeedbacks(String authUser);
}
