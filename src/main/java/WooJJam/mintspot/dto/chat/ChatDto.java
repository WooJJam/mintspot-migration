package WooJJam.mintspot.dto.chat;

import lombok.Data;

@Data
public class ChatDto {
    private String title;
    private String category;

    public ChatDto(String title, String category) {
        this.title = title;
        this.category = category;
    }
}
