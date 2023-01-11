package Backend.teampple.domain.users.service;

import Backend.teampple.domain.users.entity.UserProfile;

public interface UserService {
    void createUser(UserProfile userProfile);

    void deleteUserRefreshToken(String refreshToken);

    void deleteUser(String refreshToken);

    String getTasks(String refreshToken);

    String getFeedbacks(String refreshToken);
}
