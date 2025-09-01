package co.com.crediauth.auth;

import co.com.crediauth.model.user.gateways.UserPasswordEncryptionGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class PasswordEncryptionAdapter implements UserPasswordEncryptionGateway {
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<String> encodePassword(String plainPassword) {
        return Mono.fromCallable(() -> {
            return passwordEncoder.encode(plainPassword);
        });
    }

}
