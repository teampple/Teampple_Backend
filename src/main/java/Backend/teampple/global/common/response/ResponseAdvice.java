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
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
@Slf4j
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    private final String[] SwaggerPatterns = {
            "swagger",
            "/v2/api-docs"
    };

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

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
        ContentCachingRequestWrapper cachingRequestWrapper
                = new ContentCachingRequestWrapper(((ServletServerHttpRequest) request).getServletRequest());

//         스웨거일 경우 리스폰스 처리 안하도록
        for (String swaggerPattern : SwaggerPatterns) {
            if (cachingRequestWrapper.getRequestURI().contains(swaggerPattern))
                return body;
        }

        HttpStatus resolve = HttpStatus.resolve(servletResponse.getStatus());

        if (resolve == null)
            return body;

        if (resolve.is2xxSuccessful())
            return CommonResponse.onSuccess(statusProvider(cachingRequestWrapper.getMethod()), body);


        return body;
    }

    private int statusProvider(String method) {
        if (method.equals("POST"))
            return 201;
        if (method.equals("DELETE"))
            return 204;
        return 200;
    }
}
