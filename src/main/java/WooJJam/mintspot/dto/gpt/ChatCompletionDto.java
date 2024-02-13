package WooJJam.mintspot.dto.gpt;

import lombok.Data;

import java.util.List;

@Data
public class ChatCompletionDto {
    private String model;
    private List<ChatRequestMsgDto> messages;

    public ChatCompletionDto(String model, List<ChatRequestMsgDto> messages) {
        this.model = model;
        this.messages = messages;
    }
}
