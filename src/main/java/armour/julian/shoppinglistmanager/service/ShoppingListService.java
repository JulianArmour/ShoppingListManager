package armour.julian.shoppinglistmanager.service;

import armour.julian.shoppinglistmanager.model.ShoppingList;
import armour.julian.shoppinglistmanager.model.User;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface ShoppingListService {

    /**
     * Finds a {@linkplain ShoppingList} with a given ID
     * in the database and returns it wrapped in an {@linkplain Optional)}.
     *
     * @param id the list's ID
     * @return a {@link ShoppingList}
     */
    Optional<ShoppingList> getShoppingListById(@NonNull Long id);

    List<ShoppingList> getShoppingListsByCreator(@NonNull User user);

    List<ShoppingList> getShoppingListsSharedWithUser(User user);

    /**
     * @param listId the id of the {@linkplain ShoppingList} containing the item.
     * @param itemId the id of the {@linkplain armour.julian.shoppinglistmanager.model.ShoppingItem}.
     */
    void markItemAsComplete(Long listId, Long itemId);

    void deleteShoppingListById(Long listId);
}
