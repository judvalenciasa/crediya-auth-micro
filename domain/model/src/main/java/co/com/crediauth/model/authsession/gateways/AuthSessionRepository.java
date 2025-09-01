package co.com.crediauth.model.authsession.gateways;

import co.com.crediauth.model.authsession.AuthSession;
import reactor.core.publisher.Mono;

public interface AuthSessionRepository {
    Mono<AuthSession> createSession(String accessToken, String refreshToken, String email, String role, Long userId, String fullName);
    Mono<AuthSession> getSessionByToken(String token);
    Mono<Boolean> invalidateSession(String token);
}
