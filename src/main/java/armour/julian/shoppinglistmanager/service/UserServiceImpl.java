package armour.julian.shoppinglistmanager.service;

import armour.julian.shoppinglistmanager.model.User;
import armour.julian.shoppinglistmanager.repository.ShoppingListRepository;
import armour.julian.shoppinglistmanager.repository.UserRepository;
import armour.julian.shoppinglistmanager.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.hibernate.Hibernate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ShoppingListRepository shoppingListRepository;

    public User registerNewUser(String username, String password) {
        val user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public User getLoggedInUser(boolean loadCreatedLists, boolean loadSharedLists) {
        val userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        val user = userPrincipal.getUser();
        if (loadCreatedLists) {
            user.setCreatedShoppingLists(shoppingListRepository.findShoppingListsByListCreator(user));
        }
        if (loadSharedLists) {
            Hibernate.initialize(user.getSharedLists());
        }
        return user;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
