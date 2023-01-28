package Backend.teampple.domain.auth.oauth;

import Backend.teampple.domain.auth.dto.response.ResponseTokenDto;
import Backend.teampple.domain.auth.jwt.JwtTokenProvider;
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

/**OAuth 인증에 성공하면 시큐리티가 접근 시켜주는 클래스*/
@Slf4j
@RequiredArgsConstructor
@Component
public class OAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        /**인증에 성공한 사용자*/
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User);

        /**JwtToken 생성*/
        ResponseTokenDto responseTokenDto = jwtTokenProvider.generateToken(authentication);

        /**JwtToken 과 함께 리다이렉트*/
        String targetUrl = UriComponentsBuilder.fromUriString("https://localhost:8080/api/oauth/kakao/success")
                .queryParam("jwtAccessToken", responseTokenDto.getJwtAccessToken())
                .queryParam("jwtRefreshToken", responseTokenDto.getJwtRefreshToken())
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
