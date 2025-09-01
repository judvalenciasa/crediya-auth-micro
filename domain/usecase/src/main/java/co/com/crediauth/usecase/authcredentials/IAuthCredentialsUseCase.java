package co.com.crediauth.usecase.authcredentials;

import co.com.crediauth.model.authcredentials.AuthCredentials;
import co.com.crediauth.model.user.User;
import reactor.core.publisher.Mono;

public interface IAuthCredentialsUseCase {
    Mono<User> authenticateUser(AuthCredentials credentials);
    Mono<Boolean> validateCredentials(AuthCredentials credentials);
}
