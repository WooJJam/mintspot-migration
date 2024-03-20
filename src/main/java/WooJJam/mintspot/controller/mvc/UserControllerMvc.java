package WooJJam.mintspot.controller.mvc;

import WooJJam.mintspot.domain.user.Gender;
import WooJJam.mintspot.domain.user.Sexual;
import WooJJam.mintspot.domain.user.User;
import WooJJam.mintspot.dto.user.UserLoginRequestBodyDto;
import WooJJam.mintspot.dto.user.UserRegisterRequestBodyDto;
import WooJJam.mintspot.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public String register(@ModelAttribute UserRegisterRequestBodyDto userRegisterRequestBodyDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("email", userRegisterRequestBodyDto.getEmail());
        Long userId = userService.register(userRegisterRequestBodyDto);
        return "redirect:/chat/"+userId;
    }

    @GetMapping("/login")
    public String loginRenderView(Model model) {
        model.addAttribute("user", new User());
        System.out.println("login");
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginRequestBodyDto userLoginRequestBodyDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = userService.login(userLoginRequestBodyDto);
        session.setAttribute("email", userLoginRequestBodyDto.getEmail());
        return "redirect:/chat/"+userId;
    }

}
