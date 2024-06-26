package WooJJam.mintspot.domain.user;

import WooJJam.mintspot.domain.Profile;
import WooJJam.mintspot.domain.chat.Chat;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Chat> chats = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;


    public void createUser(String email, String password, Gender gender, Sexual sexual, Profile profile) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.sexual = sexual;
        this.profile = profile;
        this.profile.createUser(this);
    }

}
