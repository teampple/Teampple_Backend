package Backend.teampple.domain.users;

import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.domain.users.repository.UserProfileRepository;
import Backend.teampple.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

//JWT 후 업데이트
    public UserProfile getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저 입니다"));
        return user.getUserProfile();
    }

    @Transactional
    public void updateUserProfile(Long userId){
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저 입니다"));
//      update 쿼리 생성
    }

    public String getTasks(Long userId) {
        return "";
    }

    public String getFeedbacks(Long userId) {
        return "";
    }
}
