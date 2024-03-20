package WooJJam.mintspot.dto.user;

import WooJJam.mintspot.domain.user.Gender;
import WooJJam.mintspot.domain.user.Sexual;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class UserRegisterRequestBodyDto {

    @Email(message = "이메일 형식만 입력할 수 있습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 빈칸일 수 없습니다.")
    @Size(min = 6, max = 20, message = "비밀번호는 6 ~ 20 자 입니다.")
    private String password;

    private Gender gender;
    private Sexual sexual;
}
