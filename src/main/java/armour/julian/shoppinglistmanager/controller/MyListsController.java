package armour.julian.shoppinglistmanager.controller;

import armour.julian.shoppinglistmanager.model.ShoppingItem;
import armour.julian.shoppinglistmanager.model.ShoppingList;
import armour.julian.shoppinglistmanager.model.User;
import armour.julian.shoppinglistmanager.service.ShoppingItemService;
import armour.julian.shoppinglistmanager.service.ShoppingListService;
import armour.julian.shoppinglistmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Controller
@RequestMapping("/mylists")
@RequiredArgsConstructor
public class MyListsController {
    private final UserService userService;
    private final ShoppingListService shoppingListService;
    private final ShoppingItemService shoppingItemService;

    @ModelAttribute("user")
    public User currentlyLoggedInUser() {
        return userService.getLoggedInUser(true, false);
    }

    @ModelAttribute("lists")
    public List<ShoppingList> userLists() {
        return currentlyLoggedInUser().getCreatedShoppingLists();
    }

    @ModelAttribute("newList")
    public ShoppingList newList() {
        // add a ShoppingList object for the "New List" form
        return new ShoppingList();
    }

    @GetMapping("")
    public String listManager() {
        return "user-lists";
    }

    @PostMapping("/create")
    public String createShoppingList(@ModelAttribute ShoppingList newList) {
        User currentlyLoggedInUser = userService.getLoggedInUser(true, false);
        currentlyLoggedInUser.addCreatedList(newList);
        newList.setListCreator(currentlyLoggedInUser);
        userService.save(currentlyLoggedInUser);
        return "redirect:/mylists";
    }

    @GetMapping("/{id}")
    public String viewList(@PathVariable("id") Long id, Model model) {
        Optional<ShoppingList> shoppingListToView = shoppingListService.getShoppingListById(id);
        if (!shoppingListToView.isPresent()) {
            return "redirect:/mylists";
        }
        ShoppingList list = shoppingListToView.get();
        shoppingListService.loadPermittedEditors(list);
        fillViewListModel(model, list);
        return "view-list";
    }

    public void fillViewListModel(Model model, ShoppingList shoppingListToView) {
        model.addAttribute("permittedEditors", shoppingListToView.getPermittedEditors());
        model.addAttribute("list", shoppingListToView);
        // form model attribute for adding a new ShoppingItem
        model.addAttribute("newListItem", new ShoppingItem());
        // form model attribute for completing (checking-off) a ShoppingItem
        model.addAttribute("completedItem", new ShoppingItem());
        // 'Share' button user model
        model.addAttribute("sharedUser", new User());
    }

    @PostMapping("/{listId}/add-item")
    public String addListItem(@PathVariable Long listId, @ModelAttribute("newListItem") ShoppingItem newItem) {
        shoppingListService.addItemToList(newItem, listId);
        return "redirect:/mylists/" + listId;
    }

    @PostMapping("/{listId}/items/{itemId}/mark-done")
    public String completeShoppingListItem(@PathVariable Long listId, @PathVariable Long itemId) {
        shoppingListService.markItemAsComplete(listId, itemId);
        return "redirect:/mylists/" + listId;
    }

    @PostMapping("/{listId}/delete")
    public String deleteShoppingList(@PathVariable Long listId) {
        shoppingListService.deleteShoppingListById(listId);
        return "redirect:/mylists";
    }

    @PostMapping(value = "/{listId}/add-permitted-editor")
    public String addPermittedEditor(@PathVariable Long listId, @ModelAttribute("sharedUser") User user) {
        shoppingListService.addPermittedEditorToList(user.getUsername(), listId);
        return "redirect:/mylists/" + listId;
    }
}
