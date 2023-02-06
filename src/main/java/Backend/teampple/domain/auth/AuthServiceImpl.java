package Backend.teampple.domain.auth;

import Backend.teampple.domain.auth.dto.request.RequestJwtTokenDto;
import Backend.teampple.domain.auth.dto.response.ResponseJwtTokenDto;
import Backend.teampple.domain.auth.jwt.JwtTokenProvider;
import Backend.teampple.domain.auth.inmemory.RefreshTokenRepository;
import Backend.teampple.domain.users.entity.User;
import Backend.teampple.domain.users.service.UserService;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public ResponseJwtTokenDto login() {
//        final ResponseTokenDto generateToken = jwtTokenProvider.generateToken(authentication);
        return ResponseJwtTokenDto.builder().build();
    }

    @Override
    @Transactional
    public void logout(User user, RequestJwtTokenDto requestJwtTokenDto) {
        /**refreshToken 만료 여부 확인*/
        if (!refreshTokenRepository.existsById(requestJwtTokenDto.getJwtRefreshToken())) {
            throw new UnauthorizedException(ErrorCode.INVALID_REFRESH_TOKEN.getMessage());
        }

        refreshTokenRepository.deleteById(requestJwtTokenDto.getJwtRefreshToken());
        SecurityContextHolder.clearContext();
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

        /**refreshToken 만료 여부 확인*/
        if (!refreshTokenRepository.existsById(requestJwtTokenDto.getJwtRefreshToken())) {
            throw new UnauthorizedException(ErrorCode.INVALID_REFRESH_TOKEN.getMessage());
        }

        /** accessToken 생성용 Authentication 생성 */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Authentication reissueAuthentication = new UsernamePasswordAuthenticationToken(user.getAuthKey(), user.getPassword(), authentication.getAuthorities());
        log.info(reissueAuthentication.getName());

        final ResponseJwtTokenDto generateToken = ResponseJwtTokenDto.builder()
                .jwtAccessToken(jwtTokenProvider.generateAccessToken(reissueAuthentication, new Date()))
                .jwtRefreshToken(requestJwtTokenDto.getJwtRefreshToken())
                .build();

        return generateToken;
    }
}
