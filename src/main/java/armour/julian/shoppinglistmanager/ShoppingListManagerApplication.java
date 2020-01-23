package armour.julian.shoppinglistmanager;

import armour.julian.shoppinglistmanager.model.ShoppingItem;
import armour.julian.shoppinglistmanager.model.ShoppingList;
import armour.julian.shoppinglistmanager.model.User;
import armour.julian.shoppinglistmanager.repository.ShoppingListRepository;
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
    public CommandLineRunner initUser(ShoppingListRepository listRepository,
                                      UserRepository userRepository,
                                      PasswordEncoder passwordEncoder) {
        return (args) -> {
            val user = new User();
            user.setUsername("test");
            user.setPassword(passwordEncoder.encode("pass"));

            val list = new ShoppingList();
            list.setName("Groceries");
            list.setDescription("This week's grocery list");
            list.addItem(new ShoppingItem("Broccoli", 3));
            list.addItem(new ShoppingItem("Steak", 5));

            val list2 = new ShoppingList();
            list2.setName("Clothes");
            list2.setDescription("Buy shirts");
            list2.addItem(new ShoppingItem("Blue Polo", 1));

            user.addCreatedList(list);
            list.setListCreator(user);
            user.addCreatedList(list2);
            list2.setListCreator(user);

            userRepository.save(user);

            // User #2

            val user2 = new User();
            user2.setUsername("user2");
            user2.setPassword(passwordEncoder.encode("user2"));
            userRepository.save(user2);

            val list3 = new ShoppingList();
            list3.setName("Pharmacy");
            list3.setDescription("pharmacy list");
            list3.addItem(new ShoppingItem("Toothpaste", 2));

            user2.addCreatedList(list3);
            list3.setListCreator(user2);
            // share the list
            list3.addPermittedEditor(user);
            listRepository.save(list3);
            user.addListSharedWithThisUser(list3);
            userRepository.save(user);
            userRepository.save(user2);
        };
    }
}
