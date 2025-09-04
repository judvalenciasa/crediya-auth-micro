package co.com.crediauth.auth.security;

import co.com.crediauth.model.seguridad.LoginRequest;
import co.com.crediauth.model.user.User;
import co.com.crediauth.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class Credentials {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<User> authenticateUser(LoginRequest credentials) {
        return userRepository.findByEmail(credentials.email())
                .filter(user -> passwordEncoder.matches(credentials.password(), user.getPassword()))
                .switchIfEmpty(Mono.empty());
    }
}
