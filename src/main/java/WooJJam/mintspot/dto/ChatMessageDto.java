package WooJJam.mintspot.dto;

import WooJJam.mintspot.domain.chat.Category;
import WooJJam.mintspot.domain.chat.Chat;
import WooJJam.mintspot.domain.user.User;
import WooJJam.mintspot.dto.user.UserDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ChatMessageDto {

    private Long id;
//    private UserDto user;
//    private String title;
//    private Category category;
    private List<MessageDto> messages;
    public ChatMessageDto(Chat chat) {
        this.id = chat.getId();
//        this.title = chat.getTitle();
//        this.category = chat.getCategory();
//        this.user = new UserDto(chat.getUser());
        this.messages = chat.getMessages().stream()
                .map(message -> new MessageDto(message.getId(), message.getContent()))
                .collect(Collectors.toList());
    }
}
