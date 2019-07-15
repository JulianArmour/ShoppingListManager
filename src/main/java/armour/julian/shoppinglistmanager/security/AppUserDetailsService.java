package armour.julian.shoppinglistmanager.security;

import armour.julian.shoppinglistmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        val user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }
}
