package armour.julian.shoppinglistmanager.service;

import armour.julian.shoppinglistmanager.model.ShoppingList;
import armour.julian.shoppinglistmanager.model.User;
import armour.julian.shoppinglistmanager.repository.ShoppingListRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ShoppingListServiceImpl implements ShoppingListService {
    private final ShoppingListRepository shoppingListRepo;

    @Override
    public Optional<ShoppingList> getShoppingListById(@NonNull Long id) {
        return shoppingListRepo.findById(id);
    }

    @Override
    public List<ShoppingList> getShoppingListsSharedWithUser(User user) {
        return shoppingListRepo.findShoppingListsByPermittedEditors(user);
    }

    @Override
    public List<ShoppingList> getShoppingListsByCreator(@NonNull User user) {
        return shoppingListRepo.findShoppingListsByListCreator(user);
    }
}
