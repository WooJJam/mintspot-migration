package WooJJam.mintspot.dto;

import WooJJam.mintspot.domain.Message;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChatMessageDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;

    public ChatMessageDto(Long id, String content, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
    }
}
