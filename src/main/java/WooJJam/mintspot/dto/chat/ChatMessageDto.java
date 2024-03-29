package WooJJam.mintspot.dto.chat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter @Setter
public class ChatMessageDto {

    private String content;
    private LocalDateTime createdAt;

    public ChatMessageDto(String content, LocalDateTime createdAt) {
        this.content = content;
        this.createdAt = createdAt;
    }
}
