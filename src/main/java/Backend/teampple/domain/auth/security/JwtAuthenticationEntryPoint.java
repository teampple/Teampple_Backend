package Backend.teampple.domain.auth.security;

import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
/**
 * user 정보가 없는 경우
 * */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        throw new UnauthorizedException(ErrorCode.INVALID_AUTH_TOKEN, ErrorCode.INVALID_AUTH_TOKEN.getMessage());
    }
}
