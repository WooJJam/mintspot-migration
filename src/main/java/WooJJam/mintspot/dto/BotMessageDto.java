package WooJJam.mintspot.dto;

import WooJJam.mintspot.domain.Message;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BotMessageDto {
    private String userContent;
    private String botContent;
    private LocalDateTime createdAt;

    public BotMessageDto(String userContent, String botContent, LocalDateTime createdAt) {
        this.userContent = userContent;
        this.botContent = botContent;
        this.createdAt = createdAt;
    }

//    public BotMessageDto2(String userContent, String botContent) {
//        this.userContent = userContent;
//        this.botContent = botContent;
//    }
}
