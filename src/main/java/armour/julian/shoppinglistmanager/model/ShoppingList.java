package armour.julian.shoppinglistmanager.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "shopping_list")
@Data
public class ShoppingList {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_list_id")
    @Column(name = "items")
    List<ShoppingItem> items;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_creator_id")
    private User listCreator;

    @ManyToMany(mappedBy = "sharedLists")
    private Set<User> permittedEditors;
}
