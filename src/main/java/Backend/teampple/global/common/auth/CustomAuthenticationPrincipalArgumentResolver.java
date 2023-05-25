package Backend.teampple.global.common.auth;

import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.UnauthorizedException;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;

@Component
public class CustomAuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private ExpressionParser parser = new SpelExpressionParser();

    private BeanResolver beanResolver;

    /**받아온 파라미터 타입이 설정한 타입과 같을 경우 true 반환*/
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return findMethodAnnotation(AuthUser.class, parameter) != null;
    }

    /**실제 바인딩할 객체를 만들어 반환*/
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            /**
             * 기존 값 = return null;
             * Error 반환 하기 위해 커스텀
             * */
            throw new UnauthorizedException(ErrorCode.INVALID_PRINCIPAL);
        }
        Object principal = authentication.getPrincipal();
        AuthUser annotation = findMethodAnnotation(AuthUser.class, parameter);
        String expressionToParse = annotation.expression();
        if (StringUtils.hasLength(expressionToParse)) {
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setRootObject(principal);
            context.setVariable("this", principal);
            context.setBeanResolver(this.beanResolver);
            Expression expression = this.parser.parseExpression(expressionToParse);
            principal = expression.getValue(context);
        }
        if (principal != null && !ClassUtils.isAssignable(parameter.getParameterType(), principal.getClass())) {
            if (annotation.errorOnInvalidType()) {
                throw new ClassCastException(principal + " is not assignable to " + parameter.getParameterType());
            }
            throw new UnauthorizedException(ErrorCode.INVALID_PRINCIPAL);
        }
        return principal;
    }

    private <T extends Annotation> T findMethodAnnotation(Class<T> annotationClass, MethodParameter parameter) {
        T annotation = parameter.getParameterAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }
        Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
        for (Annotation toSearch : annotationsToSearch) {
            annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(), annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }
}
