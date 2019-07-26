package armour.julian.shoppinglistmanager.controller;

import armour.julian.shoppinglistmanager.model.ShoppingList;
import armour.julian.shoppinglistmanager.model.User;
import armour.julian.shoppinglistmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mylists")
@RequiredArgsConstructor
public class MyListsController {
    private final UserService userService;

    @GetMapping("")
    public String listManager(Model model) {
        User currentlyLoggedInUser = userService.getLoggedInUser(true, false);
        model.addAttribute("user", currentlyLoggedInUser);
        model.addAttribute("lists", currentlyLoggedInUser.getCreatedShoppingLists());
        // add a ShoppingList object for the "New List" form
        model.addAttribute("newList", new ShoppingList());
        return "user-lists";
    }

    @PostMapping("/create")
    public String createShoppingList(@ModelAttribute ShoppingList newList) {
        User currentlyLoggedInUser = userService.getLoggedInUser(true, false);
        currentlyLoggedInUser.addCreatedList(newList);
        userService.save(currentlyLoggedInUser);
        return "redirect:/mylists";
    }

    @GetMapping("/mylists/{id}")
    public String viewList(@PathVariable("id") Long id, Model model) {
        //TODO
        return "list-view";
    }
}
