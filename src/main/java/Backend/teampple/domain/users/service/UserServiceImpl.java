package Backend.teampple.domain.users.service;

import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserProfileService userProfileService;

    @Transactional
    public void createUser(UserProfile userProfile) {
        User.builder()
                .userProfile(userProfile)
//                .refreshToken()
                .expRT(LocalDateTime.now().plusWeeks(2L))
                .build();
    }

    @Override
    public void deleteUserRefreshToken(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저 입니다"));
        user.deleteRefreshToken();
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String refreshToken){
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저 입니다"));
        userProfileService.deleteUserProfile(user.getUserProfile());
        user.updateIsDeleted();
        userRepository.save(user);
    }

    public String getTasks(String refreshToken) {
        return "";
    }

    public String getFeedbacks(String refreshToken) {
        return "";
    }
}
