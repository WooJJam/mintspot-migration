package WooJJam.mintspot.dto.chat;

import WooJJam.mintspot.domain.chat.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter @Setter
public class ChatCreateRequestBodyDto {
    private String email;
    private String title;
    private Category category;

    public ChatCreateRequestBodyDto(String email, Category category) {
        this.email = email;
        this.category = category;
        this.title = LocalDateTime.now().toString();
    }
}
