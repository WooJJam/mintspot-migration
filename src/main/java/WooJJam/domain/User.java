package WooJJam.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String gender;
    private String sexual;
}
