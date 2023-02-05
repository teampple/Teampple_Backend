package Backend.teampple.domain.auth;

import Backend.teampple.domain.auth.dto.request.RequestJwtTokenDto;
import Backend.teampple.domain.auth.dto.response.ResponseJwtTokenDto;
import Backend.teampple.domain.auth.jwt.JwtTokenProvider;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.domain.users.service.UserService;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.BadRequestException;
import Backend.teampple.global.error.exception.NotFoundException;
import Backend.teampple.global.error.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public ResponseJwtTokenDto login() {
//        final ResponseTokenDto generateToken = jwtTokenProvider.generateToken(authentication);
        return ResponseJwtTokenDto.builder().build();
    }

    @Override
    @Transactional
    public void logout(User user) {
        /**이미 로그아웃 되어있는 유저인지*/
        if (user.getRefreshToken() == null) {
            throw new BadRequestException();
        }

        SecurityContextHolder.clearContext();
        userService.deleteUserRefreshToken(user);
    }

    @Override
    @Transactional
    public ResponseJwtTokenDto join() {
//        final ResponseTokenDto generateToken = jwtTokenProvider.generateToken(authentication);
        return ResponseJwtTokenDto.builder().build();
    }

    @Override
    @Transactional
    public void withdrawal(User user) {
        userService.deleteUser(user);
    }

    @Override
    @Transactional
    public ResponseJwtTokenDto reIssuance(User user, RequestJwtTokenDto requestJwtTokenDto) {
        /**refreshToken 유효성 확인*/
        if (!jwtTokenProvider.validateToken(requestJwtTokenDto.getJwtRefreshToken())) {
            throw new UnauthorizedException(ErrorCode.INVALID_TOKEN.getMessage());
        }
        /** userDetails 조회*/
        Authentication authentication = jwtTokenProvider.getAuthentication(requestJwtTokenDto.getJwtAccessToken());

        /** 로그아웃 확인 */
        if (user.getRefreshToken() == null) {
            throw new UnauthorizedException(ErrorCode.REFRESH_TOKEN_NOT_FOUND.getMessage());
        }

        /** refreshToken 유효성 확인 */
        if (!user.getRefreshToken().equals(requestJwtTokenDto.getJwtRefreshToken()) || user.getExpRT().before(new Date())) {
            throw new UnauthorizedException(ErrorCode.INVALID_REFRESH_TOKEN.getMessage());
        }

        final ResponseJwtTokenDto generateToken = ResponseJwtTokenDto.builder()
                .jwtAccessToken(jwtTokenProvider.generateAccessToken(authentication, new Date()))
                .jwtRefreshToken(requestJwtTokenDto.getJwtRefreshToken())
                .build();

        return generateToken;
    }
}
