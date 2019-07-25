package armour.julian.shoppinglistmanager.service;

import armour.julian.shoppinglistmanager.model.ShoppingList;
import armour.julian.shoppinglistmanager.model.User;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ShoppingListService {

    /**
     * Finds a {@linkplain ShoppingList} with a given ID
     * in the database and returns it wrapped in an {@linkplain Optional)}.
     *
     * @param id the list's ID
     * @return a {@link ShoppingList}
     */
    Optional<ShoppingList> getShoppingListById(@NonNull Long id);

    List<ShoppingList> getCreatedShoppingListsForCreator(@NonNull User user);

    Set<ShoppingList> getSharedShoppingListsForUser(User user);
}
