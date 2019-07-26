package armour.julian.shoppinglistmanager.repository;

import armour.julian.shoppinglistmanager.model.ShoppingList;
import armour.julian.shoppinglistmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
    List<ShoppingList> findShoppingListsByListCreator(User listCreator);
    List<ShoppingList> findShoppingListsByPermittedEditors(User user);
}
