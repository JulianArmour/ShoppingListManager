package armour.julian.shoppinglistmanager.controller;

import armour.julian.shoppinglistmanager.model.ShoppingItem;
import armour.julian.shoppinglistmanager.model.ShoppingList;
import armour.julian.shoppinglistmanager.model.User;
import armour.julian.shoppinglistmanager.service.ShoppingListService;
import armour.julian.shoppinglistmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/sharedwithme")
@RequiredArgsConstructor
public class SharedWithMeController {
    private final ShoppingListService shoppingListService;
    private final UserService userService;

    @GetMapping("")
    public String listManager(Model model) {
        User currentlyLoggedInUser = userService.getLoggedInUser(false, true);
        model.addAttribute("user", currentlyLoggedInUser);
        model.addAttribute("lists", currentlyLoggedInUser.getListsSharedWithThisUser());
        // add a ShoppingList object for the "New List" form
        model.addAttribute("newList", new ShoppingList());
        return "user-lists";
    }

    @GetMapping("/{id}")
    public String viewList(@PathVariable("id") Long id) {
        return "redirect:/mylists/" + id;
    }
}