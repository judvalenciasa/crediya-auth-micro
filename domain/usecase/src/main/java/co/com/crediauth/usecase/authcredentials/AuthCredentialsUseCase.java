package co.com.crediauth.usecase.authcredentials;

import co.com.crediauth.model.authcredentials.AuthCredentials;
import co.com.crediauth.model.authcredentials.gateways.AuthCredentialsRepository;
import co.com.crediauth.model.user.User;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import javax.naming.AuthenticationException;

@RequiredArgsConstructor
public class AuthCredentialsUseCase implements IAuthCredentialsUseCase{
    private final AuthCredentialsRepository authCredentialsRepository;

    @Override
    public Mono<User> authenticateUser(AuthCredentials credentials) {
        return authCredentialsRepository.authenticateUser(credentials)
                .switchIfEmpty(Mono.error(new AuthenticationException("Credenciales inválidas")))
                .flatMap(user -> {
                    if (!user.isEnabled()) {
                        return Mono.error(new AuthenticationException("Usuario deshabilitado"));
                    }
                    return Mono.just(user);
                });
    }

    @Override
    public Mono<Boolean> validateCredentials(AuthCredentials credentials) {
        return authCredentialsRepository.validateCredentials(credentials);
    }
}
