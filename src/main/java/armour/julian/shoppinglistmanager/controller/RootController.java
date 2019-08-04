package armour.julian.shoppinglistmanager.controller;

import armour.julian.shoppinglistmanager.controller.Exceptions.UserAlreadyExistsException;
import armour.julian.shoppinglistmanager.model.User;
import armour.julian.shoppinglistmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class RootController {
    private final UserService userService;

    @GetMapping("/")
    public String indexRedirect() {
        return "redirect:/mylists";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "registered", required = false) String registeredMsg, Model model) {
        if (registeredMsg != null) {
            model.addAttribute("registered", true);
        } else {
            model.addAttribute("registered", false);
        }
        return "login";
    }

    @GetMapping("/register")
    public String registrationPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        model.addAttribute("newUser", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("newUser") User newUser) {
        userService.registerNewUser(newUser.getUsername(), newUser.getPassword());
        return "redirect:/login?registered=true";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExistsException(Exception e) {
        return "redirect:register?error="+e.getMessage();
    }
}
