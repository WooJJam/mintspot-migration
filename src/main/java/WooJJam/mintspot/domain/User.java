package WooJJam.mintspot.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Enumerated(value = EnumType.STRING)
    private Sexual sexual;

    public void createUser(String email, String password, Gender gender, Sexual sexual) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.sexual =sexual;
    }
}
