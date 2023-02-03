package Backend.teampple.domain.users.service;

import Backend.teampple.domain.users.dto.request.PutUserProfileDto;
import Backend.teampple.domain.users.dto.response.GetUserProfileDto;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.domain.users.mapper.request.GetUserProfileMapper;
import Backend.teampple.domain.users.mapper.response.PutUserProfileMapper;
import Backend.teampple.domain.users.repository.UserProfileRepository;
import Backend.teampple.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;

    private final GetUserProfileMapper getUserProfileMapper;
    private final PutUserProfileMapper putUserProfileMapper;

    @Override
    @Transactional
    public UserProfile createProfile(String name) {
        UserProfile userProfile = UserProfile.builder()
                .name(name)
                .profileImage(String.valueOf((int) (Math.random() * 12) + 1))
                .build();
        return userProfileRepository.save(userProfile);
    }

    @Override
    public GetUserProfileDto getUserProfile(User user) {
        return getUserProfileMapper.toDto(user.getUserProfile());
    }

    @Override
    @Transactional
    public GetUserProfileDto updateUserProfile(User user, PutUserProfileDto putUserProfileDto) {
        UserProfile userProfile = user.getUserProfile();

        putUserProfileMapper.updateFromDto(putUserProfileDto, userProfile);
        UserProfile save = userProfileRepository.save(userProfile);

        return getUserProfileMapper.toDto(save);
    }

    @Override
    @Transactional
    public void deleteUserProfile(UserProfile userProfile) {
        userProfile.updateIsDeleted();
        userProfileRepository.save(userProfile);
    }

}
