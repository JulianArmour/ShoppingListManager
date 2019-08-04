package armour.julian.shoppinglistmanager.service;

import armour.julian.shoppinglistmanager.model.ShoppingList;
import armour.julian.shoppinglistmanager.model.User;

import java.util.List;

public interface UserService {
    List<User> findPermittedListEditors(ShoppingList shoppingList);

    void registerNewUser(String username, String password);

    User getLoggedInUser(boolean loadCreatedLists, boolean loadSharedLists);

    /**
     * Saves or updates a user in the database.
     * @param user a user to save to the database
     */
    void save(User user);
}
