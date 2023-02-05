package Backend.teampple.domain.auth.security;

import static Backend.teampple.global.error.FilterExceptionHandler.setResponse;
import Backend.teampple.global.error.ErrorCode;
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
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {
        //TODO: 세부적으로 나누어야함
        setResponse(response,ErrorCode.UNAUTHORIZED_ACCESS);
    }
}
