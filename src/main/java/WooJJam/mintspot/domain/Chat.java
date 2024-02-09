package WooJJam.mintspot.domain;

import WooJJam.mintspot.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Chat {
    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String category;

    public void setUser(User user) {
        this.user = user;
    }
    public void createChatRoom(String title, String category, User user) {
        this.title = title;
        this.category = category;
        this.user = user;
        user.getChat().add(this);
    }
}
