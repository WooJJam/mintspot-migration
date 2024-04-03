package WooJJam.mintspot.domain;

import WooJJam.mintspot.domain.chat.Category;
import WooJJam.mintspot.domain.chat.Chat;
import WooJJam.mintspot.domain.user.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Bot {

    @Id @GeneratedValue
    @Column(name = "system_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Column(length = 1500)
    private String content;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    public void createBot(Chat chat, String content) {
        this.chat = chat;
        this.chat.getBot().add(this);
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

}
