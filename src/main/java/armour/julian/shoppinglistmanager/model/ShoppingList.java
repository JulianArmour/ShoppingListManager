package armour.julian.shoppinglistmanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "shopping_list")
public class ShoppingList {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Getter @Setter
    private Long id;

    @Column(name = "name")
    @Getter @Setter
    private String name;

    @Column(name = "description")
    @Getter @Setter
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_list_id")
    @Column(name = "items")
    @Getter @Setter
    List<ShoppingItem> items;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "list_creator_id")
    @Getter @Setter
    private User listCreator;

    @ManyToMany
    @JoinTable(name = "user_shopping_list",
            joinColumns = @JoinColumn(name = "shopping_list_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Getter @Setter
    private Set<User> permittedEditors;

    public ShoppingList() {
        this.items = new ArrayList<>();
        this.permittedEditors = new HashSet<>();
    }

    public void addItem(ShoppingItem item) {
        this.items.add(item);
    }

    public void addPermittedEditor(User newPermittedEditor) {
        this.permittedEditors.add(newPermittedEditor);
    }

    public Optional<ShoppingItem> getItem(Long itemId) {
        return getItems().stream().filter(shoppingItem -> itemId.equals(shoppingItem.getId())).findAny();
    }

    public Long numberOfCompletedItems() {
        return getItems().stream().filter(ShoppingItem::isCompleted).count();
    }

    public Long numberOfIncompleteItems() {
        return getItems().size() - numberOfCompletedItems();
    }
}
