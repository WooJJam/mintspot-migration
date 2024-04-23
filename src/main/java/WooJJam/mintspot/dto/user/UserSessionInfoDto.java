package WooJJam.mintspot.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSessionInfoDto {
    private Long userId;
    private String email;
    private String profileUrl;
}
