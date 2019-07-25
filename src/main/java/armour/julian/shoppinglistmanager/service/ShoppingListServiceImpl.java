package armour.julian.shoppinglistmanager.service;

import armour.julian.shoppinglistmanager.model.ShoppingList;
import armour.julian.shoppinglistmanager.model.User;
import armour.julian.shoppinglistmanager.repository.ShoppingListRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class ShoppingListServiceImpl implements ShoppingListService {
    private final ShoppingListRepository shoppingListRepo;

    @Override
    public Optional<ShoppingList> getShoppingListById(@NonNull Long id) {
        return shoppingListRepo.findById(id);
    }

    @Override
    public Set<ShoppingList> getSharedShoppingListsForUser(User user) {
        return shoppingListRepo.findShoppingListsByPermittedEditorsContaining(user);
    }

    @Override
    public List<ShoppingList> getCreatedShoppingListsForCreator(@NonNull User user) {
        return shoppingListRepo.findShoppingListsByListCreator(user);
    }
}
