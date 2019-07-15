package armour.julian.shoppinglistmanager.repository;

import armour.julian.shoppinglistmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
