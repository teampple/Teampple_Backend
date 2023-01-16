package Backend.teampple.domain.auth;

import Backend.teampple.domain.auth.dto.request.RequestJwtTokenDto;
import Backend.teampple.domain.auth.dto.request.RequestOAuthTokenDto;
import Backend.teampple.domain.auth.dto.response.ResponseTokenDto;
import Backend.teampple.domain.auth.jwt.JwtTokenProvider;
import Backend.teampple.domain.users.dto.request.PostUserProfileDto;
import Backend.teampple.domain.users.entity.UserProfile;
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

    @Override
    @Transactional
    public ResponseTokenDto login(RequestOAuthTokenDto requestOAuthTokenDto) {
        //TODO: 카카오 유효성 확인
        Authentication authentication = forcedAuthentication(requestOAuthTokenDto);
        final ResponseTokenDto generateToken = jwtTokenProvider.generateToken(authentication);

        userService.updateUserRefreshToken(requestOAuthTokenDto.getIdToken(), generateToken.getRefreshToken());
        return generateToken;
    }

    @Override
    public void logout(Authentication authentication) {
        userService.deleteUserRefreshToken(authentication.getName());
    }

    @Override
    @Transactional
    public ResponseTokenDto join(RequestOAuthTokenDto requestOAuthTokenDto, PostUserProfileDto postUserProfileDto) {
        //TODO: 카카오 유효성 확인
        Authentication authentication = forcedAuthentication(requestOAuthTokenDto);
        final ResponseTokenDto generateToken = jwtTokenProvider.generateToken(authentication);

        UserProfile profile = userProfileService.createProfile(postUserProfileDto);
        userService.createUser(profile, requestOAuthTokenDto.getIdToken(), generateToken.getRefreshToken());
        return generateToken;
    }

    @Override
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

        if(user.getRefreshToken().equals(requestJwtTokenDto.getJwtRefreshToken()) || user.getExpRT().isBefore(LocalDateTime.now())){
            throw new BadRequestException();
        }

        final ResponseTokenDto generateToken = ResponseTokenDto.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(authentication, new Date()))
                .refreshToken(requestJwtTokenDto.getJwtRefreshToken())
                .build();

        return generateToken;
    }

    private Authentication forcedAuthentication(RequestOAuthTokenDto requestOAuthTokenDto) {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        final User user = new User(requestOAuthTokenDto.getIdToken(), "", roles);

        final Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, roles);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
