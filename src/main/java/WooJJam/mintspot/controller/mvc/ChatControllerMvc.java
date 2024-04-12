package WooJJam.mintspot.controller.mvc;

import WooJJam.mintspot.domain.chat.Category;
import WooJJam.mintspot.domain.chat.Chat;
import WooJJam.mintspot.domain.user.User;
import WooJJam.mintspot.dto.chat.ChatCreateRequestBodyDto;
import WooJJam.mintspot.repository.ChatRepository;
import WooJJam.mintspot.repository.UserRepository;
import WooJJam.mintspot.service.ChatGptService;
import WooJJam.mintspot.service.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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

    @GetMapping("/bot")
    public String chat(Model model,
                       @RequestParam("param") Category category,
                       HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String email = user.getEmail();
        ChatCreateRequestBodyDto chatCreateRequestBodyDto = new ChatCreateRequestBodyDto(email, category);
        chatService.createChat(chatCreateRequestBodyDto);
        log.info("Chat Info = {}", chatCreateRequestBodyDto.toString());
        return "chatBot";
    }

    @ResponseBody
    @PostMapping("/bot")
    public String bot() {
        System.out.println("success");
        return "success";
    }
}
