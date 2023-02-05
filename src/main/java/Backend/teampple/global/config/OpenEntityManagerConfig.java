package Backend.teampple.global.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class OpenEntityManagerConfig {
    /** security filter 가 OSIV interceptor 보다 먼저 실행되어
     * 영속성 컨텍스트가 종료된 상황인 @AuthenticationPrincipal 이 작동될 때,
     * User 객체가 준영속 상태
     * => filter interceptor 순서 변경으로 해결 interceptor 를 filter 로 동작할 수 있게 함
     */
    @Bean
    public FilterRegistrationBean<OpenEntityManagerInViewFilter> openEntityManagerInViewFilter() {
        FilterRegistrationBean<OpenEntityManagerInViewFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new OpenEntityManagerInViewFilter());
        filterFilterRegistrationBean.setOrder(Integer.MIN_VALUE);
        return filterFilterRegistrationBean;
    }
}
