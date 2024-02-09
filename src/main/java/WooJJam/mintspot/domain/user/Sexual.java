package WooJJam.mintspot.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sexual {
    HETEROSEXUAL("이성애자"),
    BISEXUAL("양성애자"),
    HOMOSEXUAL("동성애자");

    private final String description;
}
