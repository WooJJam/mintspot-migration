package WooJJam.mintspot.dto.chat;

import WooJJam.mintspot.domain.chat.Category;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatCreateRequestBodyDto {
    private String email;
    private String title;
    private Category category;
}
