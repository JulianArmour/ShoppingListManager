package armour.julian.shoppinglistmanager.service;

import armour.julian.shoppinglistmanager.model.User;
import armour.julian.shoppinglistmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User registerNewUser(String username, String password) {
        val user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }
}
