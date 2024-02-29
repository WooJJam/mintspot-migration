package WooJJam.mintspot.service;

import WooJJam.mintspot.config.jwt.JwtProvider;
import WooJJam.mintspot.domain.user.User;
import WooJJam.mintspot.dto.TokenDto;
import WooJJam.mintspot.dto.user.UserLoginRequestBodyDto;
import WooJJam.mintspot.dto.user.UserRegisterRequestBodyDto;
import WooJJam.mintspot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Transactional
    public Long register(UserRegisterRequestBodyDto userRegisterRequestBodyDto) {
        User user = new User();
        user.createUser(
                userRegisterRequestBodyDto.getEmail(),
                userRegisterRequestBodyDto.getPassword(),
                userRegisterRequestBodyDto.getGender(),
                userRegisterRequestBodyDto.getSexual()
        );
        return userRepository.register(user);
    }

    public ResponseEntity<?> login(UserLoginRequestBodyDto userLoginRequestBodyDto) {
        String email = userLoginRequestBodyDto.getEmail();
        String password = userLoginRequestBodyDto.getPassword();
        User findUser = userRepository.findByEmail(email);
        if (findUser != null && findUser.getPassword().equals(password)) {
            String accessToken = jwtProvider.createAccessToken();
            String refreshToken = jwtProvider.createRefreshToken();
            userRepository.login(findUser, refreshToken);
            return ResponseEntity.ok(findUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("NOT EXIST USER");
        }
    }

    public TokenDto createToken() {
        return new TokenDto(jwtProvider.createAccessToken(), jwtProvider.createRefreshToken());
    }

    public String verify(String authorization) {
        return jwtProvider.getTokenInfo(authorization);
    }
}
