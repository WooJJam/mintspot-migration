package WooJJam.mintspot.dto.chat;

import WooJJam.mintspot.domain.chat.Category;
import lombok.Data;

@Data
public class ChatListDto {
    private String title;
    private Category category;

    public ChatListDto(String title, Category category) {
        this.title = title;
        this.category = category;
    }
}
