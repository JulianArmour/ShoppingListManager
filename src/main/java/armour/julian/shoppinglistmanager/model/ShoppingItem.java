package armour.julian.shoppinglistmanager.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "shopping_item")
@Data
public class ShoppingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    /**
     * Marks that a list item has been per-say "added to cart"
     * or purchased.
     */
    @Column(name = "complete")
    private Boolean complete;

    public ShoppingItem() {
        this.complete = false;
    }

    public ShoppingItem(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
        this.complete = false;
    }

    public Boolean isCompleted() {
        return getComplete();
    }
}
