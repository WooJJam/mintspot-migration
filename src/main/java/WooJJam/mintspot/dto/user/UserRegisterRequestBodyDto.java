package WooJJam.mintspot.dto.user;

import WooJJam.mintspot.domain.Gender;
import WooJJam.mintspot.domain.Sexual;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class UserRegisterRequestBodyDto {

    private String email;
    private String password;
    private Gender gender;
    private Sexual sexual;
}
