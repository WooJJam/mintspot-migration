package WooJJam.mintspot.controller.mvc;

import WooJJam.mintspot.domain.chat.Chat;
import WooJJam.mintspot.service.ChatGptService;
import WooJJam.mintspot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatControllerMvc {

    private final ChatService chatService;

    @GetMapping("/{userId}")
    public String chatRenderView(
            @PathVariable("userId") Long userId,
            Model model) {
        System.out.println("????");
        List<Chat> chats = chatService.listChat();
        model.addAttribute("chats", chats);
        model.addAttribute("userId", userId);
        return "chat";
    }
}
