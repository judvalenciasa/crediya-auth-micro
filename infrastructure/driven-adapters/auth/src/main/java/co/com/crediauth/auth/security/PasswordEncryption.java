package co.com.crediauth.auth.security;

import co.com.crediauth.model.user.gateways.UserPasswordEncryptionGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PasswordEncryption  implements UserPasswordEncryptionGateway {
    private final PasswordEncoder passwordEncoder ;

    public Mono<String> encodePassword(String plainPassword) {
        return Mono.fromCallable(() -> {
            return passwordEncoder.encode(plainPassword);
        });
    }
}
