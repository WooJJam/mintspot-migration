package WooJJam.mintspot.domain.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    BODY ("몸 상담"),
    LIFE ("성 생활 상담"),
    IDENTITY("성적 자아 상담"),
    NONE("자유 상담");

    private final String description;
}
