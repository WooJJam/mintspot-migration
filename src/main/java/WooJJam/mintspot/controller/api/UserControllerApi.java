package WooJJam.mintspot.controller.api;

import WooJJam.mintspot.domain.user.User;
import WooJJam.mintspot.dto.user.UserDto;
import WooJJam.mintspot.dto.user.UserLoginRequestBodyDto;
import WooJJam.mintspot.dto.user.UserRegisterRequestBodyDto;
import WooJJam.mintspot.repository.UserRepository;
import WooJJam.mintspot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserControllerApi {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public Long register(@RequestBody UserRegisterRequestBodyDto userRegisterRequestBodyDto) {
        return userService.register(userRegisterRequestBodyDto);
    }

    @PostMapping("/login")
    public Optional<Long> login(@RequestBody UserLoginRequestBodyDto userLoginRequestBodyDto) {
        return userService.login(userLoginRequestBodyDto);
    }

    @GetMapping("/chat/list")
    public List<UserDto> chatList() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(u -> new UserDto(u))
                .collect(Collectors.toList());
    }
}
