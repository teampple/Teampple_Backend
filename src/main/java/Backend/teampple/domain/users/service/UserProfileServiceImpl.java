package Backend.teampple.domain.users.service;

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
//TODO: refreshToken 유효성 확인
public class UserProfileServiceImpl implements UserProfileService {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    private final GetUserProfileMapper getUserProfileMapper;
    private final PostUserProfileMapper postUserProfileMapper;
    private final PutUserProfileMapper putUserProfileMapper;

    @Override
    @Transactional
    public UserProfile createProfile(PostUserProfileDto postUserProfileDto) {
        UserProfile userProfile = postUserProfileMapper.toEntity(postUserProfileDto);
        return userProfileRepository.save(userProfile);
    }

    @Override
    public GetUserProfileDto getUserProfile(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저 입니다"));
        return getUserProfileMapper.toDto(user.getUserProfile());
    }

    @Override
    @Transactional
    public GetUserProfileDto updateUserProfile(String refreshToken, PutUserProfileDto putUserProfileDto) {
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저 입니다"));
        UserProfile userProfile = user.getUserProfile();
        putUserProfileMapper.updateFromDto(putUserProfileDto, userProfile);
        UserProfile save = userProfileRepository.save(userProfile);
        return getUserProfileMapper.toDto(save);
    }

    @Override
    @Transactional
    public void deleteUserProfile(UserProfile userProfile){
        userProfile.updateIsDeleted();
        userProfileRepository.save(userProfile);
    }

}
