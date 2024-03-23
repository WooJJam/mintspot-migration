package WooJJam.mintspot.controller.mvc;

import WooJJam.mintspot.domain.chat.Chat;
import WooJJam.mintspot.domain.user.User;
import WooJJam.mintspot.repository.ChatRepository;
import WooJJam.mintspot.repository.UserRepository;
import WooJJam.mintspot.service.ChatGptService;
import WooJJam.mintspot.service.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
            Model model,
            HttpServletRequest request) {
        List<Chat> chats = chatService.listChat(userId);
        model.addAttribute("chats", chats);
        model.addAttribute("userId", userId);
        return "chat";
    }
}
