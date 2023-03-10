package Backend.teampple.domain.auth.oauth;

import Backend.teampple.domain.auth.dto.JwtTokenDto;
import Backend.teampple.domain.auth.jwt.JwtTokenProvider;
import Backend.teampple.domain.auth.inmemory.service.RefreshTokenService;
import Backend.teampple.domain.auth.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

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
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        /**인증에 성공한 사용자*/
        CustomUserDetails oAuth2User = (CustomUserDetails) authentication.getPrincipal();
        log.info("Principal 에서 꺼낸 OAuth2User = {}", oAuth2User.getUser().getAuthKey());

        /**JwtToken 생성*/
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateToken(authentication);

        /**RefreshToken update*/
        refreshTokenService.saveRefreshToken(jwtTokenDto.getJwtRefreshToken(),oAuth2User.getUser().getAuthKey());

        log.info(request.getServerName());
        log.info(setRedirectUrl(request.getServerName()));

        /**JwtToken 과 함께 리다이렉트*/
        String targetUrl = UriComponentsBuilder.fromUriString(setRedirectUrl(request.getServerName()))
                .queryParam("jwtAccessToken", jwtTokenDto.getJwtAccessToken())
                .queryParam("jwtRefreshToken", jwtTokenDto.getJwtRefreshToken())
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    /**
     * Redirect url set
     */
    private String setRedirectUrl(String url) {
        String redirect_url = null;
        if (url.equals("localhost")) {
            redirect_url = "http://localhost:8080/api/oauth/kakao/success";
        }
        if (url.equals("teampple.site")) {
            redirect_url = "http://localhost:3000/oauth/kakao/success/ing";
        }
        if (url.equals("teampple.com")) {
            redirect_url = "https://www.teampple.com/oauth/kakao/success/ing";
        }

        return redirect_url;
    }
}
