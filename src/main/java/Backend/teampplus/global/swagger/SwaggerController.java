package Backend.teampplus.global.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class SwaggerController {
    @GetMapping("/api/api-docs")
    public String redirectSwagger(){return "redirect:/swagger-ui/index.html";}
}
