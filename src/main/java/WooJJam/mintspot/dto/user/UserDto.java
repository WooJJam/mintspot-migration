package WooJJam.mintspot.dto.user;

import WooJJam.mintspot.domain.user.Gender;
import WooJJam.mintspot.domain.user.Sexual;
import WooJJam.mintspot.domain.user.User;
import WooJJam.mintspot.dto.chat.ChatDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {

    private Long userId;
    private String email;
    private String password;
    private Gender gender;
    private Sexual sexual;
    private List<ChatDto> chats;

    public UserDto(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.gender = user.getGender();
        this.sexual = user.getSexual();
        this.chats = user.getChats().stream()
                .map(chat -> new ChatDto(chat.getTitle(), chat.getCategory()))
                .collect(Collectors.toList());
    }
}
