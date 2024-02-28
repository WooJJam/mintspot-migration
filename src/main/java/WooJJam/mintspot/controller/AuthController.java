package WooJJam.mintspot.controller;

import WooJJam.mintspot.dto.TokenDto;
import WooJJam.mintspot.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/token")
    public TokenDto createAccessToken() {
        return authService.createToken();
    }

}
