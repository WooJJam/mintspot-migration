package WooJJam.mintspot.domain;

import WooJJam.mintspot.domain.chat.Chat;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Message {

    @Id @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Column(length = 1500)
    private String content;

    public void createMessage(Chat chat, String content) {
        this.chat = chat;
        this.content = content;
        this.chat.getMessages().add(this);
    }
}
