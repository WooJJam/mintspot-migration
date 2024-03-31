package WooJJam.mintspot.controller.api;

import WooJJam.mintspot.dto.BotMessageDto;
import WooJJam.mintspot.dto.chat.ChatCreateRequestBodyDto;
import WooJJam.mintspot.dto.chat.ChatDto;
import WooJJam.mintspot.dto.chat.ChatMessageRequestDto;
import WooJJam.mintspot.service.ChatGptService;
import WooJJam.mintspot.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatControllerApi {

    private final ChatService chatService;
    private final ChatGptService chatgptService;

    @PostMapping("/create")
    public void createChat(@RequestBody ChatCreateRequestBodyDto chatCreateRequestBodyDto) {
        this.chatService.createChat(chatCreateRequestBodyDto);
    }

    @GetMapping("/{chatId}")
    public List<ChatDto> listMessage(
            @PathVariable("chatId") Long chatId,
            @RequestBody Map<String, Integer> range) {
        System.out.println("range = " + range);
        return this.chatgptService.listMessage(chatId, range.get("offset"), range.get("limit"));
    }

    @PostMapping("/{id}/send-message")
    public BotMessageDto sendMessage(
            @PathVariable("id") Long chatId,
            @RequestBody ChatMessageRequestDto chatMessageRequestDto) throws JsonProcessingException, ParseException {
        return this.chatgptService.sendMessage(chatId, chatMessageRequestDto);
    }

}
