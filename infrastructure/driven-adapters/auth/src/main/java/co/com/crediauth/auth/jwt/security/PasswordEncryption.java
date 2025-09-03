package co.com.crediauth.auth.jwt.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PasswordEncryption{
    private final PasswordEncoder passwordEncoder ;

    public Mono<String> encodePassword(String plainPassword) {
        return Mono.fromCallable(() -> {
            return passwordEncoder.encode(plainPassword);
        });
    }
}
