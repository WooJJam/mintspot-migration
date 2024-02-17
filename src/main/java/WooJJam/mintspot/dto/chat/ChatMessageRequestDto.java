package WooJJam.mintspot.dto.chat;

import WooJJam.mintspot.domain.chat.Category;
import WooJJam.mintspot.domain.user.Gender;
import lombok.Data;

@Data
public class ChatMessageRequestDto {
    private Gender gender;
    private Category category;
    private String userContent;
}
