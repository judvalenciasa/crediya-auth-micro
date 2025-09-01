package co.com.crediauth.auth;

import co.com.crediauth.model.authtoken.AuthToken;
import co.com.crediauth.model.authtoken.gateways.AuthTokenRepository;
import co.com.crediauth.model.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthTokenAdapter implements AuthTokenRepository {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Value("${jwt.refresh-expiration}")
    private Long jwtRefreshExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Mono<AuthToken> generateToken(User user) {
        return Mono.fromCallable(() -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime accessTokenExpiration = now.plusSeconds(jwtExpiration / 1000);
            LocalDateTime refreshTokenExpiration = now.plusSeconds(jwtRefreshExpiration / 1000);

            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user.getId());
            claims.put("email", user.getEmail());
            claims.put("roleId", user.getRolId());
            claims.put("fullName", user.getNames() + " " + user.getLastNames());

            String accessToken = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getEmail())
                    .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                    .setExpiration(Date.from(accessTokenExpiration.atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                    .compact();

            String refreshToken = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getEmail())
                    .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                    .setExpiration(Date.from(refreshTokenExpiration.atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                    .compact();

            return new AuthToken(accessToken, refreshToken, accessTokenExpiration, refreshTokenExpiration, user.getId(), user.getEmail(), user.getRolId().toString());
        });
    }

    @Override
    public Mono<Boolean> validateToken(String token) {
        return Mono.fromCallable(() -> {
            try {
                Jwts.parserBuilder()
                        .setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(token);
                return true;
            } catch (JwtException | IllegalArgumentException e) {
                log.warn("Token validation failed: {}", e.getMessage());
                return false;
            }
        });
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public Mono<AuthToken> refreshToken(String refreshToken) {
        return validateToken(refreshToken)
                .flatMap(valid -> {
                    if (!valid) {
                        return Mono.error(new RuntimeException("Refresh token inválido"));
                    }
                    return getEmailFromToken(refreshToken)
                            .flatMap(email -> Mono.just(new AuthToken(refreshToken, refreshToken, LocalDateTime.now(), LocalDateTime.now(), 1L, email, "1")));
                });
    }
}