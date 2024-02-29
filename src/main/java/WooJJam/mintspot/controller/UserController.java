package WooJJam.mintspot.controller;

import WooJJam.mintspot.domain.user.User;
import WooJJam.mintspot.dto.user.UserDto;
import WooJJam.mintspot.dto.user.UserLoginRequestBodyDto;
import WooJJam.mintspot.dto.user.UserRegisterRequestBodyDto;
import WooJJam.mintspot.repository.UserRepository;
import WooJJam.mintspot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/chat/list")
    public List<UserDto> chatList() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(u -> new UserDto(u))
                .collect(Collectors.toList());
    }

}
