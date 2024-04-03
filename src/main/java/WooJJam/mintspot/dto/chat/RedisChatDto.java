package WooJJam.mintspot.dto.chat;

import WooJJam.mintspot.domain.Bot;
import WooJJam.mintspot.domain.Message;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RedisChatDto {
    private int index;
    private String title;
    private String userMessage;
    private String botMessage;

//    public RedisChatDto(int index, String title, String userMessage, String botMessage) {
//        this.index = index;
//        this.title = title;
//        this.userMessage = userMessage;
//        this.botMessage = botMessage;
//    }
}
