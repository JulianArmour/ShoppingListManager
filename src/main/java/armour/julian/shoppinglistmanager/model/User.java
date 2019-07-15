package armour.julian.shoppinglistmanager.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
}
