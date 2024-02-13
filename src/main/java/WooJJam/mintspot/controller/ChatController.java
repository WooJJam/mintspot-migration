package WooJJam.mintspot.controller;

import WooJJam.mintspot.dto.chat.ChatCreateRequestBodyDto;
import WooJJam.mintspot.dto.gpt.ChatCompletionDto;
import WooJJam.mintspot.service.ChatGptService;
import WooJJam.mintspot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @INPUT
     * {
     *     "model": "gpt-3.5-turbo",
     *     "messages": [
     *         {
     *             "role": "system",
     *             "content": "넌 한국어로만 대답하는 프로그래밍 봇이야."
     *         },
     *         {
     *             "role":"user",
     *             "content":"자바가 뭐야?"
     *         }
     *     ]
     * }
     */
    @GetMapping("/send-message")
    public ResponseEntity<String> sendMessage(@RequestBody ChatCompletionDto chatCompletionDto) {
        return this.chatgptService.sendMessage(chatCompletionDto);
    }

}
