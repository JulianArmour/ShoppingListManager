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

    @Column(name = "complete")
    private Boolean complete;

    public ShoppingItem() {
    }

    public ShoppingItem(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
