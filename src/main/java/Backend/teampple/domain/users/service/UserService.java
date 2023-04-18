package Backend.teampple.domain.users.service;

import Backend.teampple.domain.users.dto.response.GetUserFeedbacksDto;
import Backend.teampple.domain.users.dto.response.GetUserTasksDto;
import Backend.teampple.domain.users.dto.response.GetUserTeamsDto;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.entity.UserProfile;

public interface UserService {
    User createUser(UserProfile userProfile, String authKey);

    void deleteUser(User user);

    GetUserTasksDto getUserTasks(User authUser);

    GetUserTeamsDto getUserTeams(User authUser, boolean isActive);

    GetUserFeedbacksDto getUserFeedbacks(User authUser);
}
