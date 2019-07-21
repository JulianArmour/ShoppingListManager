package armour.julian.shoppinglistmanager.service;

import armour.julian.shoppinglistmanager.model.User;

public interface UserService {
    User registerNewUser(String username, String password);
    User getLoggedInUser(boolean loadCreatedLists, boolean loadSharedLists);
}
