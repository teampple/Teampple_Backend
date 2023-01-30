package Backend.teampple.domain.auth.oauth;

import Backend.teampple.domain.auth.dto.response.ResponseTokenDto;
import Backend.teampple.domain.auth.jwt.JwtTokenProvider;
import Backend.teampple.domain.users.repository.UserRepository;
import Backend.teampple.domain.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OAuth 인증에 성공하면 시큐리티가 접근 시켜주는 클래스
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class OAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        /**인증에 성공한 사용자*/
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User);

        /**JwtToken 생성*/
        ResponseTokenDto responseTokenDto = jwtTokenProvider.generateToken(authentication);

        /**회원 가입 여부 확인 및 RefreshToken update*/
        if (!userRepository.existsByKakaoId(oAuth2User.getName())) {
//            User user = oAuth2AttributeUserMapper.toDto(oAuth2Attribute);
//            userRepository.save(user);
        } else {
            userService.updateUserRefreshToken(oAuth2User.getName(), responseTokenDto.getJwtRefreshToken());
        }

        /**JwtToken 과 함께 리다이렉트*/
        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/api/oauth/kakao/success")
                .queryParam("jwtAccessToken", responseTokenDto.getJwtAccessToken())
                .queryParam("jwtRefreshToken", responseTokenDto.getJwtRefreshToken())
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
