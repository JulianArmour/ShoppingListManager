package armour.julian.shoppinglistmanager.service;

import armour.julian.shoppinglistmanager.model.ShoppingItem;
import armour.julian.shoppinglistmanager.model.ShoppingList;
import armour.julian.shoppinglistmanager.model.User;
import armour.julian.shoppinglistmanager.repository.ShoppingListRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ShoppingListServiceImpl implements ShoppingListService {
    private final ShoppingListRepository shoppingListRepo;
    private final ShoppingItemService shoppingItemService;

    @Override
    public Optional<ShoppingList> getShoppingListById(@NonNull Long id) {
        return shoppingListRepo.findById(id);
    }

    @Override
    public List<ShoppingList> getShoppingListsSharedWithUser(User user) {
        return shoppingListRepo.findShoppingListsByPermittedEditors(user);
    }

    @Override
    public void markItemAsComplete(Long listId, Long itemId) {
        Optional<ShoppingList> potentialShoppingListContainingTheItem =  shoppingListRepo.findById(listId);
        if (potentialShoppingListContainingTheItem.isPresent()) {
            ShoppingList shoppingListContainingTheItem  = potentialShoppingListContainingTheItem.get();
            Optional<ShoppingItem> theItem = shoppingListContainingTheItem.getItem(itemId);

            Consumer<ShoppingItem> setComplete = item -> item.setComplete(true);
            Consumer<ShoppingItem> saveToDb = shoppingItemService::save;
            theItem.ifPresent(setComplete.andThen(saveToDb));
        }
    }

    @Override
    public List<ShoppingList> getShoppingListsByCreator(@NonNull User user) {
        return shoppingListRepo.findShoppingListsByListCreator(user);
    }
}
