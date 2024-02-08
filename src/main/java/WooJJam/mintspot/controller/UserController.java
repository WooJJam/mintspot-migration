package WooJJam.mintspot.controller;

import WooJJam.mintspot.domain.User;
import WooJJam.mintspot.dto.user.UserRegisterRequestBodyDto;
import WooJJam.mintspot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Long register(@RequestBody UserRegisterRequestBodyDto userRegisterRequestBodyDto) {
        return userService.register(userRegisterRequestBodyDto);
    }
}
