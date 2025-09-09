package co.com.crediauth.auth.security;

import co.com.crediauth.model.seguridad.LoginResponse;
import co.com.crediauth.model.seguridad.TokenValidationResult;
import co.com.crediauth.model.seguridad.gateways.AuthGateway;
import co.com.crediauth.model.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RequiredArgsConstructor
public class Token implements AuthGateway {
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Mono<LoginResponse> generateToken(User user) {
        return Mono.fromCallable(() -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime accessTokenExpiration = now.plusSeconds(jwtExpiration * 24 * 60 * 60 * 1000);

            Map<String, Object> claims = new HashMap<>();

            claims.put("role", user.getRolId());
            claims.put("userId", user.getId());

            String accessToken = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                    .setExpiration(Date.from(accessTokenExpiration.atZone(ZoneId.systemDefault()).toInstant()))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                    .compact();


            return new LoginResponse(
                    accessToken,
                    accessTokenExpiration,
                    user.getRolId()
            );
        });
    }

    @Override
    public Mono<Boolean> authenticateUser(String credentialPassword, String userPassword) {
        if (passwordEncoder.matches(credentialPassword, userPassword)) {
            return Mono.just(true);
        } else {
            return Mono.just(false);
        }
    }


    public Mono<String> encodePassword(String plainPassword) {
        return Mono.fromCallable(() -> {
            return passwordEncoder.encode(plainPassword);
        });
    }


    @Override
    public Mono<TokenValidationResult> validateTokenAndExtractRole(String token) {
        return Mono.fromCallable(() -> {
            try {
                var claims = Jwts.parserBuilder()
                        .setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                Long role = claims.get("role", Long.class);
                Long userId = claims.get("userId", Long.class);

                return new TokenValidationResult(
                        true,
                        role,
                        "Token válido",
                        userId

                );


            } catch (Exception e) {
                return new TokenValidationResult(
                        false,
                        null,
                        "Token inválido: " + e.getMessage(),
                        null
                );
            }
        });
    }


}
