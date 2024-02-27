package WooJJam.mintspot.util;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.Password;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtUtils {

    public void buildJwt() {
        String SECRET_KEY = "G1PA0eysF7VX4MghEEW4YYVS4OwPHtuO9TCEdw5raTQ"; // 256bit key를 직접 생성
        byte[] keyBytes = SECRET_KEY.getBytes();
        Key key = Keys.hmacShaKeyFor(keyBytes);
        String jwt= Jwts.builder()
                .header()
                .type("AccessToken")
                .add("email", "test")
                .and()
                .issuer("우재민")
                .issuedAt(new Date())
                .claim("claim","이건 테스트용 claim")
                .signWith(key)
                .compact();
        System.out.println("jwt = " + jwt);
    }
}
