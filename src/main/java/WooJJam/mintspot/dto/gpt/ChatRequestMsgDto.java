package WooJJam.mintspot.dto.gpt;

import lombok.Data;

@Data
public class ChatRequestMsgDto {
    private String role;
    private String content;

    public ChatRequestMsgDto(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
