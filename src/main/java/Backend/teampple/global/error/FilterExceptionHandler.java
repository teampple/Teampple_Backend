package Backend.teampple.global.error;

import Backend.teampple.global.common.response.CommonResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterExceptionHandler {
    /**
     * 스프링 시큐티리 예외 커스텀을 위한 함수
     */
    public static void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(CommonResponse.jsonOf(errorCode));
    }
}
