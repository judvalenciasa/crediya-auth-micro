package co.com.crediauth.usecase.authsession;

import co.com.crediauth.model.authsession.AuthSession;
import co.com.crediauth.model.authsession.gateways.AuthSessionRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AuthSessionUseCase implements IAuthSessionUseCase{
    private final AuthSessionRepository authSessionRepository;

    @Override
    public Mono<AuthSession> createSession(String accessToken, String refreshToken, String email, String role, Long userId, String fullName) {
        return authSessionRepository.createSession(accessToken, refreshToken, email, role, userId, fullName);
    }

    @Override
    public Mono<AuthSession> getSessionByToken(String token) {
        return authSessionRepository.getSessionByToken(token);
    }

    @Override
    public Mono<Boolean> invalidateSession(String token) {
        return authSessionRepository.invalidateSession(token);
    }
}
