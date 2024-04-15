package WooJJam.mintspot.domain;

import WooJJam.mintspot.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Profile {

    @Id @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    @Column
    private String fileName;

    @Column(length = 500)
    private String profileUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void createProfile(String fileName, String profileUrl) {
        this.fileName  = fileName;
        this.profileUrl = profileUrl;
    }

    public void createUser(User user) {
        this.user = user;
    }
}
