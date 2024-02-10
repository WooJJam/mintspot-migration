package WooJJam.mintspot.dto.chat;

import WooJJam.mintspot.domain.chat.Category;
import lombok.Data;

@Data
public class ChatDto {
    private String title;
    private Category category;

    public ChatDto(String title, Category category) {
        this.title = title;
        this.category = category;
    }
}
