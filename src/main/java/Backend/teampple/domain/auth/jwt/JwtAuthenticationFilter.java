package Backend.teampple.domain.auth.jwt;

import Backend.teampple.domain.auth.security.CustomUserDetails;
import Backend.teampple.global.error.ErrorCode;
import static Backend.teampple.global.error.FilterExceptionHandler.setResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        ContentCachingRequestWrapper cachingRequestWrapper
                = new ContentCachingRequestWrapper(request);
        try {
            String token = parseBearerToken(cachingRequestWrapper);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Could not set user authentication in security context", e);
            setResponse(response, ErrorCode.USER_NOT_FOUND);
        }
        chain.doFilter(cachingRequestWrapper, response);
    }

    /**
     * Request Header 에서 토큰 정보 추출
     */
    private String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}
