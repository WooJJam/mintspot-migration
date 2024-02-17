package WooJJam.mintspot.controller;

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
    @GetMapping("/{id}/send-message")
    public Object sendMessage(
            @PathVariable("id") Long chatId,
            @RequestBody ChatMessageRequestDto chatMessageRequestDto) throws JsonProcessingException, ParseException {
        System.out.println("chatMessageRequestDto = " + chatMessageRequestDto);
        System.out.println("chatId = " + chatId);
        return this.chatgptService.sendMessage(chatId, chatMessageRequestDto);
    }

}
