package co.com.crediauth.usecase.auth;

import co.com.crediauth.model.authcredentials.AuthCredentials;
import co.com.crediauth.model.authsession.AuthSession;
import co.com.crediauth.model.rol.gateways.RolRepository;
import co.com.crediauth.usecase.authcredentials.IAuthCredentialsUseCase;
import co.com.crediauth.usecase.authsession.IAuthSessionUseCase;
import co.com.crediauth.usecase.authtoken.IAuthTokenUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AuthUseCase implements IAuth{
    private final IAuthCredentialsUseCase authCredentialsUseCase;
    private final IAuthTokenUseCase authTokenUseCase;
    private final IAuthSessionUseCase authSessionUseCase;
    private final RolRepository rolRepository;

    @Override
    public Mono<AuthSession> login(AuthCredentials credentials) {

        return authCredentialsUseCase.authenticateUser(credentials)
                .flatMap(authTokenUseCase::generateToken)
                .flatMap(authToken -> rolRepository.findRolById(authToken.getUserId())
                        .flatMap(rol -> authSessionUseCase.createSession(
                                authToken.getAccessToken(),
                                authToken.getRefreshToken(),
                                authToken.getEmail(),
                                rol.getName(),
                                authToken.getUserId(),
                                getFullName(authToken.getEmail())
                        )));
    }

    @Override
    public Mono<Boolean> validateToken(String token) {
        return authTokenUseCase.validateToken(token);
    }

    @Override
    public Mono<String> getEmailFromToken(String token) {
        return authTokenUseCase.getEmailFromToken(token);
    }

    @Override
    public Mono<String> getRoleFromToken(String token) {
        return authTokenUseCase.getRoleFromToken(token);
    }

    @Override
    public Mono<Long> getUserIdFromToken(String token) {
        return authTokenUseCase.getUserIdFromToken(token);
    }

    @Override
    public Mono<AuthSession> refreshToken(String refreshToken) {

        return authTokenUseCase.refreshToken(refreshToken)
                .flatMap(authToken -> rolRepository.findRolById(authToken.getUserId())
                        .flatMap(rol -> authSessionUseCase.createSession(
                                authToken.getAccessToken(),
                                authToken.getRefreshToken(),
                                authToken.getEmail(),
                                rol.getName(),
                                authToken.getUserId(),
                                getFullName(authToken.getEmail())
                        )));
    }

    private String getFullName(String email) {
        // Por simplicidad, retornamos el email como nombre completo
        // En una implementación real, obtendrías el nombre del usuario
        return email;
    }
}
