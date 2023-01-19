package Backend.teampple.domain.auth;

import Backend.teampple.domain.auth.dto.request.RequestJwtTokenDto;
import Backend.teampple.domain.auth.dto.request.RequestOAuthTokenDto;
import Backend.teampple.domain.auth.dto.request.RequestSignUpDto;
import Backend.teampple.domain.auth.dto.response.ResponseTokenDto;
import Backend.teampple.domain.auth.jwt.JwtTokenProvider;
import Backend.teampple.domain.auth.mapper.RequestOAuthTokenMapper;
import Backend.teampple.domain.users.entity.UserProfile;
import Backend.teampple.domain.users.mapper.response.PostUserProfileMapper;
import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.domain.users.service.UserProfileService;
import Backend.teampple.domain.users.service.UserService;
import Backend.teampple.global.error.exception.BadRequestException;
import Backend.teampple.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserProfileService userProfileService;
    private final JwtTokenProvider jwtTokenProvider;

    private final PostUserProfileMapper postUserProfileMapper;
    private final RequestOAuthTokenMapper requestOAuthTokenMapper;

    @Override
    @Transactional
    public ResponseTokenDto login(RequestOAuthTokenDto requestOAuthTokenDto) {
        //TODO: 카카오 유효성 확인
        Authentication authentication = forcedAuthentication(requestOAuthTokenDto);
        final ResponseTokenDto generateToken = jwtTokenProvider.generateToken(authentication);

        userService.updateUserRefreshToken(requestOAuthTokenDto.getIdToken(), generateToken.getJwtRefreshToken());
        return generateToken;
    }

    @Override
    @Transactional
    public void logout(Authentication authentication) {
        userService.deleteUserRefreshToken(authentication.getName());
    }

    @Override
    @Transactional
    public ResponseTokenDto join(RequestSignUpDto requestSignUpDto) {
        //TODO: 카카오 유효성 확인
        UserProfile userProfile = postUserProfileMapper.toEntity(requestSignUpDto);
        RequestOAuthTokenDto requestOAuthToken = requestOAuthTokenMapper.toEntity(requestSignUpDto);

        Authentication authentication = forcedAuthentication(requestOAuthToken);
        final ResponseTokenDto generateToken = jwtTokenProvider.generateToken(authentication);

        UserProfile profile = userProfileService.createProfile(userProfile);
        userService.createUser(profile, requestOAuthToken.getIdToken(), generateToken.getJwtRefreshToken());
        return generateToken;
    }

    @Override
    @Transactional
    public void withdrawal(Authentication authentication) {
        userService.deleteUser(authentication.getName());
    }

    @Override
    @Transactional
    public ResponseTokenDto reIssuance(RequestJwtTokenDto requestJwtTokenDto) {
        /**refreshToken 유효성 확인*/
        if (!jwtTokenProvider.validateToken(requestJwtTokenDto.getJwtRefreshToken())){
            throw new BadRequestException();
        }
        /** userDetails 조회*/
        Authentication authentication = jwtTokenProvider.getAuthentication(requestJwtTokenDto.getJwtAccessToken());

        /**해당 유저 조회*/
        Backend.teampple.domain.users.entity.User user = userRepository.findByKakaoId(authentication.getName())
                        .orElseThrow(() -> new NotFoundException());

        log.info(String.valueOf(user.getExpRT().isAfter(LocalDateTime.now())));
        if(!user.getRefreshToken().equals(requestJwtTokenDto.getJwtRefreshToken()) || user.getExpRT().isBefore(LocalDateTime.now())){
            throw new BadRequestException();
        }

        final ResponseTokenDto generateToken = ResponseTokenDto.builder()
                .jwtAccessToken(jwtTokenProvider.generateAccessToken(authentication, new Date()))
                .jwtRefreshToken(requestJwtTokenDto.getJwtRefreshToken())
                .build();

        return generateToken;
    }

    private Authentication forcedAuthentication(RequestOAuthTokenDto requestOAuthTokenDto) {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        log.info(requestOAuthTokenDto.getIdToken());
        final User user = new User(requestOAuthTokenDto.getIdToken(), "", roles);

        final Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, roles);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
