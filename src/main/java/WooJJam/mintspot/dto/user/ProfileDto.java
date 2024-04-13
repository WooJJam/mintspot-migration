package WooJJam.mintspot.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@Setter
public class ProfileDto {
    private MultipartFile image;
}
