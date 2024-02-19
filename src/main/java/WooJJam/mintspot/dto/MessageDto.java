package WooJJam.mintspot.dto;

import lombok.Data;

@Data
public class MessageDto {
    private Long id;
    private String content;

    public MessageDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
