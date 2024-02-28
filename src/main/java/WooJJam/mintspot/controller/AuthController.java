package WooJJam.mintspot.controller;

import WooJJam.mintspot.dto.TokenDto;
import WooJJam.mintspot.service.AuthService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/token")
    public TokenDto createAccessToken() {
        return authService.createToken();
    }

    @GetMapping("/verify")
    public String validAccessToken(@RequestHeader("Authorization") String authorization) {
        return authService.verify(authorization);
    }

}
