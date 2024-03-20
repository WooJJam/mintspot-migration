package WooJJam.mintspot.service;

import WooJJam.mintspot.domain.chat.Category;
import WooJJam.mintspot.domain.chat.Chat;
import WooJJam.mintspot.domain.user.User;
import WooJJam.mintspot.dto.chat.ChatCreateRequestBodyDto;
import WooJJam.mintspot.repository.ChatRepository;
import WooJJam.mintspot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createChat(ChatCreateRequestBodyDto chatCreateRequestBodyDto) {
        String email = chatCreateRequestBodyDto.getEmail();
        String title = chatCreateRequestBodyDto.getTitle();
        Category category = chatCreateRequestBodyDto.getCategory();
        User findUser = userRepository.findByEmail(email);

        Chat chat = new Chat();
        chat.createChatRoom(title, category, findUser);
        this.chatRepository.create(chat);
    }

    public List<Chat> listChat() {
        return chatRepository.findAll();
    }
}
