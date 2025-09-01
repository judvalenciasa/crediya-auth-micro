package co.com.crediauth.auth;

import co.com.crediauth.model.authcredentials.AuthCredentials;
import co.com.crediauth.model.authcredentials.gateways.AuthCredentialsRepository;
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
public class AuthCredentialsAdapter implements AuthCredentialsRepository {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> authenticateUser(AuthCredentials credentials) {
        return userRepository.findByEmail(credentials.getEmail())
                .filter(user -> passwordEncoder.matches(credentials.getPassword(), user.getPassword()))
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Boolean> validateCredentials(AuthCredentials credentials) {
        return authenticateUser(credentials)
                .map(user -> true)
                .defaultIfEmpty(false);
    }
}
