package armour.julian.shoppinglistmanager.service;

import armour.julian.shoppinglistmanager.model.ShoppingItem;
import armour.julian.shoppinglistmanager.repository.ShoppingItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingItemServiceImpl implements ShoppingItemService {
    private final ShoppingItemRepository shoppingItemRepository;

    @Override
    public void save(ShoppingItem shoppingItem) {
        shoppingItemRepository.save(shoppingItem);
    }

    @Override
    public Optional<ShoppingItem> findById(Long id) {
        return shoppingItemRepository.findById(id);
    }
}
