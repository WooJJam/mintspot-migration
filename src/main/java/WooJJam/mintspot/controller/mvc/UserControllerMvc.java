package WooJJam.mintspot.controller.mvc;

import WooJJam.mintspot.domain.user.Gender;
import WooJJam.mintspot.domain.user.Sexual;
import WooJJam.mintspot.domain.user.User;
import WooJJam.mintspot.dto.user.UserRegisterRequestBodyDto;
import WooJJam.mintspot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserControllerMvc {

    private final UserService userService;
    @GetMapping("/register")
    public String registerRenderView(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("gender", Gender.values());
        model.addAttribute("sexual", Sexual.values());
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@ModelAttribute UserRegisterRequestBodyDto userRegisterRequestBodyDto) {
        userService.register(userRegisterRequestBodyDto);
        return "redirect:/chat";
    }
}
