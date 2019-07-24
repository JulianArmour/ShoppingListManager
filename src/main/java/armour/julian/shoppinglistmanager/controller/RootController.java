package armour.julian.shoppinglistmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String indexRedirect() {
        return "redirect:/mylists";
    }
}
