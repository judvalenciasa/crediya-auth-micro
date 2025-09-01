package co.com.crediauth.model.authcredentials.gateways;

import co.com.crediauth.model.authcredentials.AuthCredentials;
import co.com.crediauth.model.user.User;
import reactor.core.publisher.Mono;

public interface AuthCredentialsRepository {
    Mono<User> authenticateUser(AuthCredentials credentials);
    Mono<Boolean> validateCredentials(AuthCredentials credentials);
}
