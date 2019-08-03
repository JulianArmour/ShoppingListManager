package armour.julian.shoppinglistmanager.security;

import armour.julian.shoppinglistmanager.model.User;
import armour.julian.shoppinglistmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userToLoad = userRepo.findByUsername(username);

        if (userToLoad.isPresent()) {
            return new UserPrincipal(userToLoad.get());
        }

        throw new UsernameNotFoundException(username);
    }
}
