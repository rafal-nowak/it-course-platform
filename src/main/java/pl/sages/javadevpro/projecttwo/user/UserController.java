package pl.sages.javadevpro.projecttwo.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.sages.javadevpro.projecttwo.validation.AddValidators;

import javax.validation.groups.Default;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserDao userDao;

    public UserController(UserService userService, UserDao userDao) {
        this.userService = userService;
        this.userDao = userDao;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "login/register";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@Validated({AddValidators.class, Default.class}) User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/register";
        }
        userService.saveUser(user);
        return "redirect:/login";
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
