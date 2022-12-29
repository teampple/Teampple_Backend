package Backend.teampplus.deploy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HelloController {

    @GetMapping("/")
    public String printHello(){
        return "Hello";
    }
}
