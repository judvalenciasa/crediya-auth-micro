package co.com.crediauth.auth;

import co.com.crediauth.model.authsession.AuthSession;
import co.com.crediauth.model.authsession.gateways.AuthSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthSessionAdapter implements AuthSessionRepository {

    @Override
    public Mono<AuthSession> createSession(String accessToken, String refreshToken, String email, String role, Long userId, String fullName) {
        return Mono.just(new AuthSession(accessToken, refreshToken, email, role, userId, fullName));
    }

    @Override
    public Mono<AuthSession> getSessionByToken(String token) {
        // En una implementación real, esto buscaría en una base de datos o cache
        return Mono.empty();
    }

    @Override
    public Mono<Boolean> invalidateSession(String token) {
        // En una implementación real, esto invalidaría la sesión en una base de datos o cache
        return Mono.just(true);
    }
}
