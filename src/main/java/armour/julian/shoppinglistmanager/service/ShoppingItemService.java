package armour.julian.shoppinglistmanager.service;

import armour.julian.shoppinglistmanager.model.ShoppingItem;

import java.util.Optional;

public interface ShoppingItemService {

    /**
     * @param shoppingItem a {@link ShoppingItem} to persist to the database.
     */
    void save(ShoppingItem shoppingItem);

    /**
     * @param id id of the {@link ShoppingItem} to find.
     * @return an {@linkplain Optional} {@link ShoppingItem}.
     */
    Optional<ShoppingItem> findById(Long id);
}
