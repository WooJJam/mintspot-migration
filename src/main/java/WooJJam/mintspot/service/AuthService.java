package WooJJam.mintspot.service;

import WooJJam.mintspot.config.jwt.JwtProvider;
import WooJJam.mintspot.dto.TokenDto;
import WooJJam.mintspot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public TokenDto createToken() {
        return new TokenDto(jwtProvider.createAccessToken(), jwtProvider.createRefreshToken());
    }

    public String verify(String authorization) {
        return jwtProvider.getTokenInfo(authorization);
    }

}
