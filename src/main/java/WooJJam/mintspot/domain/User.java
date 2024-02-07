package WooJJam.mintspot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String gender;
    private String sexual;

    public void createUser(String email, String password, String gender, String sexual) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.sexual =sexual;
    }
}
