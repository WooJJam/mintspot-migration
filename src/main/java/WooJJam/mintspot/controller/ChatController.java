package WooJJam.mintspot.controller;

import WooJJam.mintspot.dto.chat.ChatCreateRequestBodyDto;
import WooJJam.mintspot.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @RequestMapping("/create")
    public void createChat(@RequestBody ChatCreateRequestBodyDto chatCreateRequestBodyDto) {
        this.chatService.createChat(chatCreateRequestBodyDto);
    }
}
