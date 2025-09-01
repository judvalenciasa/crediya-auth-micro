package co.com.crediauth.usecase.authtoken;

import co.com.crediauth.model.authtoken.AuthToken;
import co.com.crediauth.model.user.User;
import reactor.core.publisher.Mono;

public interface IAuthTokenUseCase {
    Mono<AuthToken> generateToken(User user);
    Mono<Boolean> validateToken(String token);
    Mono<String> getEmailFromToken(String token);
    Mono<String> getRoleFromToken(String token);
    Mono<Long> getUserIdFromToken(String token);
    Mono<AuthToken> refreshToken(String refreshToken);
}
