package Backend.teampple.global.common.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RestControllerAdvice
@Slf4j
public class SuccessResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    private final String[] SwaggerPatterns = {
            "swagger",
            "/v2/api-docs"
    };


    @Nullable
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response){
        HttpServletResponse servletResponse =
                ((ServletServerHttpResponse) response).getServletResponse();
        HttpServletRequest servletRequest =
                ((ServletServerHttpRequest) request).getServletRequest();

        // 스웨거일 경우 리스폰스 처리 안하도록
        for (String swaggerPattern : SwaggerPatterns) {
            if (servletRequest.getRequestURI().contains(swaggerPattern)) {
                return body;
            }
        }

        int status = servletResponse.getStatus();
        HttpStatus resolve = HttpStatus.resolve(status);

        if (resolve == null) {
            return body;
        }

        if (resolve.is2xxSuccessful()) {
            return CommonResponse.onSuccess(status, body);
        }

        return body;
    }
}
