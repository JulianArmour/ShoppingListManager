package armour.julian.shoppinglistmanager.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "shopping_list")
@Data
public class ShoppingList {

    public ShoppingList() {
        this.items = new ArrayList<>();
        this.permittedEditors = new HashSet<>();
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_list_id")
    @Column(name = "items")
    List<ShoppingItem> items;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_creator_id")
    private User listCreator;

    @ManyToMany(mappedBy = "sharedLists")
    private Set<User> permittedEditors;

    public void addItem(ShoppingItem item) {
        this.items.add(item);
    }

    public void addPermittedEditor(User newPermittedEditor) {
        this.permittedEditors.add(newPermittedEditor);
    }
}
