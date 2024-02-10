package WooJJam.mintspot.domain.chat;

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

    @Enumerated(EnumType.STRING)
    private Category category;

    public void createChatRoom(String title, Category category, User user) {
        this.title = title;
        this.category = category;
        this.user = user;
        this.user.getChats().add(this);
    }
}
