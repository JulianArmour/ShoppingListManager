package armour.julian.shoppinglistmanager.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "shopping_item")
@Data
public class ShoppingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private Long quantity;
}
