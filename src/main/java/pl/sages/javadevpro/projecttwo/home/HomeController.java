package pl.sages.javadevpro.projecttwo.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hi!!!";
    }

    @RequestMapping("/login")
    //@ResponseBody
    public String lo() {
        return "login/login";
    }
}
