package co.com.crediauth.usecase.auth;

import co.com.crediauth.model.authcredentials.AuthCredentials;
import co.com.crediauth.model.authsession.AuthSession;
import reactor.core.publisher.Mono;

public interface IAuth {
    Mono<AuthSession> login(AuthCredentials credentials);
    Mono<Boolean> validateToken(String token);
    Mono<String> getEmailFromToken(String token);
    Mono<String> getRoleFromToken(String token);
    Mono<Long> getUserIdFromToken(String token);
    Mono<AuthSession> refreshToken(String refreshToken);
}
