package Backend.teampple.domain.auth.security;

import static Backend.teampple.global.error.FilterExceptionHandler.setResponse;
import Backend.teampple.global.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
/**
 * 접근 권한이 없을 때
 * */
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        setResponse(response,ErrorCode.FORBIDDEN_USER);
    }
}
