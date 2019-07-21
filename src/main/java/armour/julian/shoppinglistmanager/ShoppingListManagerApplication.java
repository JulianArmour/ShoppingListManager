package armour.julian.shoppinglistmanager;

import armour.julian.shoppinglistmanager.model.ShoppingItem;
import armour.julian.shoppinglistmanager.model.ShoppingList;
import armour.julian.shoppinglistmanager.model.User;
import armour.julian.shoppinglistmanager.repository.UserRepository;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ShoppingListManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingListManagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner initUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return (args) -> {
            val user = new User();
            user.setUsername("test");
            user.setPassword(passwordEncoder.encode("pass"));

            val list = new ShoppingList();
            list.setName("Groceries");
            list.setDescription("This week's grocery list");
            list.addItem(new ShoppingItem("Brocoli", 3));

            user.addCreatedList(list);

            userRepository.save(user);
        };
    }
}
