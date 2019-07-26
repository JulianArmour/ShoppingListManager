package armour.julian.shoppinglistmanager.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(mappedBy = "listsSharedWithThisUser")
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
}
