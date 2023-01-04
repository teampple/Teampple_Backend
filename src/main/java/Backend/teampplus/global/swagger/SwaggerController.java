package Backend.teampplus.global.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class SwaggerController {
//     TODO : redirect 문제점 찾아보기
    @GetMapping("/api/docs")
    public String redirectSwagger(){return "redirect:/swagger-ui/index.html";}
}
