package co.com.crediauth.usecase.authsession;

import co.com.crediauth.model.authsession.AuthSession;
import reactor.core.publisher.Mono;

public interface IAuthSessionUseCase {
    Mono<AuthSession> createSession(String accessToken, String refreshToken, String email, String role, Long userId, String fullName);
    Mono<AuthSession> getSessionByToken(String token);
    Mono<Boolean> invalidateSession(String token);
}
