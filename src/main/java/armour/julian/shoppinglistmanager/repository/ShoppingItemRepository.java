package armour.julian.shoppinglistmanager.repository;

import armour.julian.shoppinglistmanager.model.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {

}
