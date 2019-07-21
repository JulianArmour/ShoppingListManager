package armour.julian.shoppinglistmanager.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    /**
     * The shopping lists this user has created.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "listCreator", orphanRemoval = true)
    private List<ShoppingList> createdShoppingLists;

    /**
     * The lists created by other users which this user has access to edit.
     */
    @ManyToMany
    @JoinTable(name = "user_shopping_list",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "shopping_list_id"))
    private Set<ShoppingList> sharedLists;

    /**
     * Adds a shopping list created by this user to their created shopping lists.
     *
     * @param createdShoppingList a shopping list created by this user
     */
    public void addCreatedList(ShoppingList createdShoppingList) {
        this.createdShoppingLists.add(createdShoppingList);
        createdShoppingList.setListCreator(this);
    }
}
