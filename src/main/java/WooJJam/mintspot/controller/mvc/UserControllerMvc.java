package WooJJam.mintspot.controller.mvc;

import WooJJam.mintspot.domain.user.Gender;
import WooJJam.mintspot.domain.user.Sexual;
import WooJJam.mintspot.domain.user.User;
import WooJJam.mintspot.dto.user.UserLoginRequestBodyDto;
import WooJJam.mintspot.dto.user.UserRegisterRequestBodyDto;
import WooJJam.mintspot.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
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
    public String register(
            @Validated @ModelAttribute("user") UserRegisterRequestBodyDto userRegisterRequestBodyDto,
            BindingResult bindingResult,
            HttpServletRequest request,
            Model model
            ) {

        System.out.println(" ==================== ");

        if (bindingResult.hasErrors()) {
            System.out.println("------------------");
            log.info("error = {}", bindingResult);
            model.addAttribute("gender", Gender.values()); // Gender enum 값 추가
            model.addAttribute("sexual", Sexual.values()); // Sexual enum 값 추가
            return "register";
        }

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

//    @PostMapping("/login")
//    public String login(@ModelAttribute UserLoginRequestBodyDto userLoginRequestBodyDto, HttpServletRequest request) {
//        Optional<Long> optionalUserId = userService.login(userLoginRequestBodyDto);
//        if (optionalUserId.isPresent()) {
//            HttpSession session = request.getSession();
//            session.setAttribute("email", userLoginRequestBodyDto.getEmail());
//            return "redirect:/chat/" + optionalUserId.get();
//        } else {
//            return "login";
//        }
//    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute UserLoginRequestBodyDto userLoginRequestBodyDto, HttpServletRequest request) {
        Optional<Long> optionalUserId = userService.login(userLoginRequestBodyDto);
        if (optionalUserId.isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute("email", userLoginRequestBodyDto.getEmail());
            return "redirect:/chat/" + optionalUserId.get();
        } else {
            return "login";
        }
    }

}
