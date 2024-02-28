package WooJJam.mintspot.service;

import WooJJam.mintspot.config.jwt.JwtProvider;
import WooJJam.mintspot.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;

    public TokenDto createToken() {
        return new TokenDto(jwtProvider.createAccessToken(), jwtProvider.createRefreshToken());
    }

}
