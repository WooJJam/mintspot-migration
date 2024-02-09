package WooJJam.mintspot.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatCreateRequestBodyDto {
    private String email;
    private String title;
    private String category;
}
