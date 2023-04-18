package Backend.teampple.global.common.auth;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
/**임시 객체 생성 여부 검토*/
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : user")
public @interface AuthUser {

    boolean errorOnInvalidType() default false;

    String expression() default "";

}
