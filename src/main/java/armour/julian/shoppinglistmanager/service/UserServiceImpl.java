package armour.julian.shoppinglistmanager.service;

import armour.julian.shoppinglistmanager.controller.Exceptions.UserAlreadyExistsException;
import armour.julian.shoppinglistmanager.model.ShoppingList;
import armour.julian.shoppinglistmanager.model.User;
import armour.julian.shoppinglistmanager.repository.UserRepository;
import armour.julian.shoppinglistmanager.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ShoppingListService shoppingListService;

    @Override
    public List<User> findPermittedListEditors(ShoppingList shoppingList) {
        return userRepository.findAllByListsSharedWithThisUser(shoppingList);
    }

    public void registerNewUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        // check if the username is already taken
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException(username);
        }

        userRepository.save(user);
    }

    @Override
    public User getLoggedInUser(boolean loadCreatedLists, boolean loadSharedLists) {
        val userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        val user = userPrincipal.getUser();

        if (loadCreatedLists) {
            user.setCreatedShoppingLists(shoppingListService.getShoppingListsByCreator(user));
        }

        if (loadSharedLists) {
            user.setListsSharedWithThisUser(new HashSet<>(shoppingListService.getShoppingListsSharedWithUser(user)));
        }
        return user;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
