package Backend.teampple.test;

import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.UnauthorizedException;
import Backend.teampple.global.common.response.CommonResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@Slf4j
@ApiIgnore
public class HelloController {

    @GetMapping("/test")
    public String test(){
        return "Hello";
    }

    @GetMapping("/")
    public CommonResponse<String> aa(@RequestBody Test test) {
//        log.info("{}", ErrorCode.TEST.getCode());
        if(test.getNum() == 1L)
            throw new UnauthorizedException();
        return new CommonResponse<String>(1,Boolean.TRUE,"1","1");
    }

    @Data
    static class Test {
        private Long num;
    }
}
