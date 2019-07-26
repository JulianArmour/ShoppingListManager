package armour.julian.shoppinglistmanager.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Getter @Setter
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @Getter @Setter
    private String username;

    @Column(name = "password", nullable = false)
    @Getter @Setter
    private String password;

    /**
     * The shopping lists this user has created.
     */
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "listCreator", orphanRemoval = true)
    @Getter @Setter
    private List<ShoppingList> createdShoppingLists;

    /**
     * The lists created by other users which this user has access to edit.
     */
    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "user_shopping_list",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "shopping_list_id"))
    @Getter @Setter
    private Set<ShoppingList> listsSharedWithThisUser;

    public User() {
        this.createdShoppingLists = new ArrayList<>();
        this.listsSharedWithThisUser = new HashSet<>();
    }

    /**
     * Adds a shopping list created by this user to their created shopping lists.
     *
     * @param createdShoppingList a shopping list created by this user
     */
    public void addCreatedList(ShoppingList createdShoppingList) {
        this.createdShoppingLists.add(createdShoppingList);
    }

    public void addListSharedWithThisUser(ShoppingList shoppingList) {
        this.listsSharedWithThisUser.add(shoppingList);
    }
}
