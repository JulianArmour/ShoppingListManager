package armour.julian.shoppinglistmanager.service;

import armour.julian.shoppinglistmanager.model.ShoppingItem;
import armour.julian.shoppinglistmanager.model.ShoppingList;
import armour.julian.shoppinglistmanager.model.User;
import armour.julian.shoppinglistmanager.repository.ShoppingListRepository;
import armour.julian.shoppinglistmanager.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ShoppingListServiceImpl implements ShoppingListService {
    private final ShoppingListRepository shoppingListRepository;
    private final UserRepository userRepository;
    private final ShoppingItemService shoppingItemService;
    private final UserService userService;

    @Override
    public Optional<ShoppingList> getShoppingListById(@NonNull Long id) {
        Optional<ShoppingList> shoppingList = shoppingListRepository.findById(id);
        shoppingList.ifPresent(list -> {
            boolean userHasAccessToList = userService.getLoggedInUser(false, false).getUsername()
                                            .equals(list.getListCreator().getUsername());
            if (!userHasAccessToList) {
                throw new AccessDeniedException("You do not have access to this list");
            }
        });
        return shoppingList;
    }

    @Override
    public List<ShoppingList> getShoppingListsSharedWithUser(User user) {
        return shoppingListRepository.findShoppingListsByPermittedEditors(user);
    }

    @Override
    public void markItemAsComplete(Long listId, Long itemId) {
        Optional<ShoppingList> potentialShoppingListContainingTheItem =  shoppingListRepository.findById(listId);
        if (potentialShoppingListContainingTheItem.isPresent()) {
            ShoppingList shoppingListContainingTheItem  = potentialShoppingListContainingTheItem.get();
            Optional<ShoppingItem> theItem = shoppingListContainingTheItem.getItem(itemId);

            Consumer<ShoppingItem> setComplete = item -> item.setComplete(true);
            Consumer<ShoppingItem> saveToDb = shoppingItemService::save;
            theItem.ifPresent(setComplete.andThen(saveToDb));
        }
    }

    @Override
    public void deleteShoppingListById(Long listId) {
        shoppingListRepository.deleteById(listId);
    }

    @Override
    public void addPermittedEditorToList(String username, Long listId) {
        Optional<User> editorToGivePermission = userRepository.findByUsername(username);
        Optional<ShoppingList> listToShare = shoppingListRepository.findById(listId);

        listToShare.ifPresent(shoppingList ->
            editorToGivePermission.ifPresent(user -> {
                shoppingList.addPermittedEditor(user);
                user.addListSharedWithThisUser(shoppingList);
                shoppingListRepository.save(shoppingList);
                userRepository.save(user);
            })
        );
    }

    @Override
    public void loadPermittedEditors(ShoppingList shoppingList) {
        shoppingList.setPermittedEditors(new HashSet<>(userRepository.findAllByListsSharedWithThisUser(shoppingList)));
    }

    @Override
    public void addItemToList(ShoppingItem newItem, Long listId) {
        shoppingListRepository.findById(listId).ifPresent(shoppingList -> {
            shoppingList.addItem(newItem);
            shoppingListRepository.save(shoppingList);
        });
    }

    @Override
    public List<ShoppingList> getShoppingListsByCreator(@NonNull User user) {
        return shoppingListRepository.findShoppingListsByListCreator(user);
    }
}
