package WooJJam.mintspot.dto.chat;

import WooJJam.mintspot.domain.Bot;
import WooJJam.mintspot.domain.Message;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter @Setter
public class ChatDto {
    private int index;
    private String title;
    private ChatMessageDto userMessage;
    private ChatMessageDto botMessage;

    public ChatDto(int index, String title, Message userMessage, Bot botMessage) {
        this.index = index;
        this.title = title;
        this.userMessage = new ChatMessageDto(userMessage.getContent(), userMessage.getCreatedAt());
        this.botMessage = new ChatMessageDto(botMessage.getContent(), botMessage.getCreatedAt());
    }
}
