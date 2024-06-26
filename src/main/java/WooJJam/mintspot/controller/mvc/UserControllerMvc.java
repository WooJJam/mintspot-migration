package WooJJam.mintspot.controller.mvc;

import WooJJam.mintspot.domain.user.Gender;
import WooJJam.mintspot.domain.user.Sexual;
import WooJJam.mintspot.domain.user.User;
import WooJJam.mintspot.dto.user.UserLoginRequestBodyDto;
import WooJJam.mintspot.dto.user.UserRegisterRequestBodyDto;
import WooJJam.mintspot.dto.user.UserSessionInfoDto;
import WooJJam.mintspot.service.UserService;
import WooJJam.mintspot.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
            @RequestParam(name = "profile") MultipartFile imageFile,
            HttpServletRequest request,
            Model model
            ) throws IOException {
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            model.addAttribute("gender", Gender.values()); // Gender enum 값 추가
            model.addAttribute("sexual", Sexual.values()); // Sexual enum 값 추가
            return "register";
        }

        HttpSession session = request.getSession();
        UserSessionInfoDto registerUser = userService.register(userRegisterRequestBodyDto, imageFile);
        session.setAttribute(SessionConst.LOGIN_USER, registerUser);
        return "redirect:/chat/"+registerUser.getUserId();
    }

    @GetMapping("/login")
    public String loginRenderView(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute("user") UserLoginRequestBodyDto userLoginRequestBodyDto,
            BindingResult bindingResult,
            HttpServletRequest request,
            Model model) {

        if (bindingResult.hasErrors()) {
            log.info("error = {}",bindingResult);
            return "login";
        }

        Optional<User> findUser = userService.login(userLoginRequestBodyDto);

        if (findUser.isPresent()) {
            User user = findUser.get();
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_USER, user);
            return "redirect:/chat/" + user.getId();
        } else {
            model.addAttribute("error", "존재하지 않는 계정이거나 비밀번호가 일치하지 않습니다.");
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);

        if (httpSession != null) {
            httpSession.invalidate();
        }

        return "redirect:/";
    }
}
