package Backend.teampple.domain.example;

import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/example")
@Component
@Api(tags = "에러 example")
public class ExampleController {

    @GetMapping("/errer")
    @Operation(summary = "에러 코드 나열")
    @ApiIgnore
    public void getGlobalErrorCode() {
        log.info("-----------시작--------------");
    }

}
