package armour.julian.shoppinglistmanager.controller;

import armour.julian.shoppinglistmanager.model.User;
import armour.julian.shoppinglistmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MyListsController {
    private final UserService userService;

    @GetMapping("/")
    public String indexRedirect() {
        return "redirect:/my-lists";
    }

    @GetMapping("/mylists")
    public String listManager(Model model) {
        User currentlyLoggedInUser = userService.getLoggedInUser(true, false);
        model.addAttribute("user", currentlyLoggedInUser);
        return "user-created-lists";
    }
}
