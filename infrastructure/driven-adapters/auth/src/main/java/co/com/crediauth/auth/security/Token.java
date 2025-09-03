package co.com.crediauth.auth.security;


import co.com.crediauth.auth.dto.TokenDto;
import co.com.crediauth.model.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Token {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Value("${jwt.refresh-expiration}")
    private Long jwtRefreshExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public Mono<TokenDto> generateToken(User user) {
        return Mono.fromCallable(() -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime accessTokenExpiration = now.plusSeconds(jwtExpiration / 1000);

            Map<String, Object> claims = new HashMap<>();

            claims.put("email", user.getEmail());
            claims.put("role", user.getRolId());
            claims.put("userId", user.getId());
            claims.put("fullName", user.getNames() + " " + user.getLastNames());

            String accessToken = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getEmail())
                    .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                    .setExpiration(Date.from(accessTokenExpiration.atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                    .compact();

            return new TokenDto(
                    accessToken,
                    accessTokenExpiration,
                    user.getEmail(),
                    user.getRolId(),
                    user.getId(),
                    user.getNames() + " " + user.getLastNames()
            );
        });
    }

    public Mono<Boolean> validateToken(String token) {
        return Mono.fromCallable(() -> {
            try {
                Jwts.parserBuilder()
                        .setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(token);
                return true;
            } catch (JwtException | IllegalArgumentException e) {
                return false;
            }
        });
    }

    public Mono<String> getEmailFromToken(String token) {
        return Mono.fromCallable(() -> {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("email", String.class);
        });
    }

    public Mono<String> getRoleFromToken(String token) {
        return Mono.fromCallable(() -> {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("roleId", String.class);
        });
    }

    public Mono<Long> getUserIdFromToken(String token) {
        return Mono.fromCallable(() -> {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("userId", Long.class);
        });
    }


    private Mono<TokenDto> generateTokenFromEmail(String email) {
        User tempUser = new User();
        tempUser.setEmail(email);

        return generateToken(tempUser);
    }




}
