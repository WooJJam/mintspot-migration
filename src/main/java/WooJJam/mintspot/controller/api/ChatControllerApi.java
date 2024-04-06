package WooJJam.mintspot.controller.api;

import WooJJam.mintspot.dto.BotMessageDto;
import WooJJam.mintspot.dto.chat.ChatCreateRequestBodyDto;
import WooJJam.mintspot.dto.chat.ChatMessageRequestDto;
import WooJJam.mintspot.dto.chat.RedisChatDto;
import WooJJam.mintspot.service.ChatGptService;
import WooJJam.mintspot.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatControllerApi {

    private final ChatService chatService;
    private final ChatGptService chatgptService;
    private final RedisTemplate<String, List<RedisChatDto>> redisTemplate;

    @PostMapping("/create")
    public void createChat(@RequestBody ChatCreateRequestBodyDto chatCreateRequestBodyDto) {
        this.chatService.createChat(chatCreateRequestBodyDto);
    }

    @GetMapping("/{chatId}")
    public List<RedisChatDto> listMessage(@PathVariable("chatId") Long chatId) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey("chat::"+chatId))) {
            return redisTemplate.opsForValue().get("chat::"+chatId);
        }
        else {
            return this.chatgptService.listMessage(chatId);
        }
    }

    @PostMapping("/{id}/send-message")
    public BotMessageDto sendMessage(
            @PathVariable("id") Long chatId,
            @RequestBody ChatMessageRequestDto chatMessageRequestDto) throws JsonProcessingException, ParseException {
        return this.chatgptService.sendMessage(chatId, chatMessageRequestDto);
    }

    @GetMapping("/cache/{chatId}")
    public String test(@PathVariable("chatId") Long chatId) {
        return this.chatgptService.cacheNewestListChatMessage(chatId);
    }

    @GetMapping("/test/{chatId}")
    public String test2(@PathVariable("chatId") Long chatId) {
        return this.chatgptService.newestListChatMessage(chatId);
    }
}