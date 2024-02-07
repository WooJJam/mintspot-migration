package WooJJam.mintspot.controller;

import WooJJam.mintspot.domain.User;
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
    public Long register(@RequestBody User user) {
        return userService.register(user);
    }
}
