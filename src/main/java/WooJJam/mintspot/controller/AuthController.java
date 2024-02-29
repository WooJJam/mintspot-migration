package WooJJam.mintspot.controller;

import WooJJam.mintspot.dto.TokenDto;
import WooJJam.mintspot.dto.user.UserLoginRequestBodyDto;
import WooJJam.mintspot.dto.user.UserRegisterRequestBodyDto;
import WooJJam.mintspot.service.AuthService;
import WooJJam.mintspot.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public Long register(@RequestBody UserRegisterRequestBodyDto userRegisterRequestBodyDto) {
        return authService.register(userRegisterRequestBodyDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestBodyDto userLoginRequestBodyDto) {
        return authService.login(userLoginRequestBodyDto);
    }

    @GetMapping("/token")
    public TokenDto createAccessToken() {
        return authService.createToken();
    }

    @GetMapping("/verify")
    public String validAccessToken(@RequestHeader("Authorization") String authorization) {
        return authService.verify(authorization);
    }

}
