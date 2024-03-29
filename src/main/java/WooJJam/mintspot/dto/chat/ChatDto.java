package WooJJam.mintspot.dto.chat;

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

    public ChatDto(int index, String title, ChatMessageDto userMessage, ChatMessageDto botMessage) {
        this.index = index;
        this.title = title;
        this.userMessage = userMessage;
        this.botMessage = botMessage;
    }
}
