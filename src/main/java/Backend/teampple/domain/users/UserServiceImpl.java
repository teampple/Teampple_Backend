package Backend.teampple.domain.users;

import Backend.teampple.domain.users.dto.request.PostUserProfileDto;
import Backend.teampple.domain.users.dto.request.PutUserProfileDto;
import Backend.teampple.domain.users.dto.response.GetUserProfileDto;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.domain.users.mapper.request.GetUserProfileMapper;
import Backend.teampple.domain.users.mapper.response.PostUserProfileMapper;
import Backend.teampple.domain.users.mapper.response.PutUserProfileMapper;
import Backend.teampple.domain.users.repository.UserProfileRepository;
import Backend.teampple.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    private final GetUserProfileMapper getUserProfileMapper;
    private final PostUserProfileMapper postUserProfileMapper;
    private final PutUserProfileMapper putUserProfileMapper;

    @Transactional
    public GetUserProfileDto signUp(PostUserProfileDto postUserProfileDto){
        UserProfile userProfile = postUserProfileMapper.toEntity(postUserProfileDto);
        UserProfile save = userProfileRepository.save(userProfile);
        return getUserProfileMapper.toDto(save);
    }

    public GetUserProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저 입니다"));
        return getUserProfileMapper.toDto(user.getUserProfile());
    }

    @Transactional
    public GetUserProfileDto updateUserProfile(Long userId, PutUserProfileDto putUserProfileDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저 입니다"));
        UserProfile userProfile = user.getUserProfile();
        putUserProfileMapper.updateFromDto(putUserProfileDto, userProfile);
        UserProfile save = userProfileRepository.save(userProfile);
        return getUserProfileMapper.toDto(save);
    }

    public String getTasks(Long userId) {
        return "";
    }

    public String getFeedbacks(Long userId) {
        return "";
    }
}
