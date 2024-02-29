package WooJJam.mintspot.domain.user;

import WooJJam.mintspot.domain.chat.Chat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
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

    private String refreshToken;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Chat> chats = new ArrayList<>();

    public void createUser(String email, String password, Gender gender, Sexual sexual) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.sexual = sexual;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
