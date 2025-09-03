package co.com.crediauth.auth.autenticacion;

import co.com.crediauth.auth.security.Credentials;
import co.com.crediauth.auth.security.Token;
import co.com.crediauth.model.seguridad.LoginRequest;

import co.com.crediauth.model.seguridad.LoginResponse;
import co.com.crediauth.usecase.seguridad.ISecurityUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class Auth implements ISecurityUseCase {
    private final Credentials credentials;
    private final Token token;

    @Override
    public Mono<LoginResponse> login(LoginRequest loginRequest) {
        log.info("Iniciando proceso de autenticación para: {}", loginRequest.email());

        return credentials.validateCredentials(loginRequest)
                .doOnNext(isValid -> log.info("Resultado validación credenciales: {}", isValid))
                .flatMap(isValid -> {
                    if (Boolean.TRUE.equals(isValid)) {
                        log.info("Credenciales válidas para: {}", loginRequest.email());
                        return credentials.authenticateUser(loginRequest)
                                .doOnNext(user -> log.info("Usuario autenticado: {}", user.getEmail()))
                                .flatMap(token::generateToken)
                                .doOnNext(tokenDto -> log.info("Tokens generados exitosamente"))
                                .map(tokenDto -> new LoginResponse(
                                        tokenDto.getAccessToken(),
                                        tokenDto.getEmail(),
                                        tokenDto.getRole().toString(),
                                        tokenDto.getUserId(),
                                        tokenDto.getFullName(),
                                        "Login exitoso"
                                ));
                    } else {
                        log.warn("Credenciales inválidas para: {}", loginRequest.email());
                        return Mono.just(new LoginResponse(
                                null, null, null, null, null,
                                "Credenciales inválidas"
                        ));
                    }
                })
                .doOnError(error -> log.error("Error durante el login: {}", error.getMessage(), error))
                .onErrorReturn(new LoginResponse(
                        null, null, null, null, null,
                        "Error interno del servidor"
                ));
    }


}
