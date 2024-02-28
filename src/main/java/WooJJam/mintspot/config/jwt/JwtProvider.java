package WooJJam.mintspot.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.naming.AuthenticationException;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${JWT_SECRET_KEY}")
    private String JWT_SECRET_KEY;
    @Value("${JWT_ISSUER}")
    private String JWT_ISSUER;
    private static final String BEARER_TYPE = "Bearer";

    public String createAccessToken() {
        long accessTokenExpireTime = 1000 * 60 * 60 * 24; // 24시간
        Key secretKey = Keys.hmacShaKeyFor(getJwtSecretKeyBytes());
        return Jwts.builder()
                .header()
                .type("AccessToken")
                .and()
                .issuer(JWT_ISSUER)
                .issuedAt(new Date())
                .expiration(getTokenExpiration(accessTokenExpireTime))
                .claim("email","테스트용 Email")
                .signWith(secretKey)
                .compact();
    }

    private Date getTokenExpiration(long expireTime) {
        return new Date(System.currentTimeMillis() + expireTime);
    }

    public String createRefreshToken() {
        long refreshTokenExpireTime = 1000 * 60 * 60 * 24 * 7;
        SecretKey secretKey = Keys.hmacShaKeyFor(getJwtSecretKeyBytes());
        return Jwts.builder()
                .header()
                .type("RefreshToken")
                .and()
                .issuer(JWT_ISSUER)
                .issuedAt(new Date())
                .expiration(getTokenExpiration(refreshTokenExpireTime))
                .signWith(secretKey)
                .compact();
    }

    public String getTokenInfo(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(getJwtSecretKeyBytes());
        String accessToken = getToken(token);
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(accessToken).getPayload().get("email", String.class);
    }

    private String getToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.split(" ")[1];
        }
        else return null;
    }

    private byte[] getJwtSecretKeyBytes() {
        return JWT_SECRET_KEY.getBytes();
    }
}
