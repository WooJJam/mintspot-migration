package WooJJam.mintspot.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatControllerMvc {

    @GetMapping
    public String chatRenderView() {
        return "chat";
    }
}
