package WooJJam.mintspot.domain.chat;

import WooJJam.mintspot.domain.Message;
import WooJJam.mintspot.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chat")
    private List<Message> messages = new ArrayList<>();

    public void createChatRoom(String title, Category category, User user) {
        this.title = title;
        this.category = category;
        this.user = user;
        this.user.getChats().add(this);
    }
}
