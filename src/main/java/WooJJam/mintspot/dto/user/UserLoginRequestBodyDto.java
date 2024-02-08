package WooJJam.mintspot.dto.user;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@ToString
public class UserLoginRequestBodyDto {

    @Email
    private String email;
    private String password;
}
