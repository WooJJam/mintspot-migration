package WooJJam.mintspot.controller;

import WooJJam.mintspot.domain.Message;
import WooJJam.mintspot.domain.chat.Chat;
import WooJJam.mintspot.dto.ChatMessageDto;
import WooJJam.mintspot.dto.chat.ChatCreateRequestBodyDto;
import WooJJam.mintspot.dto.chat.ChatMessageRequestDto;
import WooJJam.mintspot.dto.gpt.ChatResponseMsgDto;
import WooJJam.mintspot.service.ChatGptService;
import WooJJam.mintspot.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatGptService chatgptService;

    @PostMapping("/create")
    public void createChat(@RequestBody ChatCreateRequestBodyDto chatCreateRequestBodyDto) {
        this.chatService.createChat(chatCreateRequestBodyDto);
    }

    /**
     * @INPUT {
     * "model": "gpt-3.5-turbo",
     * "messages": [
     * {
     * "role": "system",
     * "content": "넌 한국어로만 대답하는 프로그래밍 봇이야."
     * },
     * {
     * "role":"user",
     * "content":"자바가 뭐야?"
     * }
     * ]
     * }
     **/

//    @GetMapping("/{id}")

    @PostMapping("/{id}/send-message")
    public String sendMessage(
            @PathVariable("id") Long chatId,
            @RequestBody ChatMessageRequestDto chatMessageRequestDto) throws JsonProcessingException, ParseException {
        return this.chatgptService.sendMessage(chatId, chatMessageRequestDto);
    }

    @GetMapping("/{id}")
    public List<ChatMessageDto> listMessage(@PathVariable("id") Long chatId) {
        return this.chatgptService.listMessage(chatId);
    }

}
