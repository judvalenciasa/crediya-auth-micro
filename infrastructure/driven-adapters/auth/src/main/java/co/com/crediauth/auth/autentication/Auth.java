package co.com.crediauth.auth.autentication;

import co.com.crediauth.auth.security.Credentials;
import co.com.crediauth.auth.security.PasswordEncryption;
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
    private final PasswordEncryption passwordEncryption;

    @Override
    public Mono<LoginResponse> login(LoginRequest loginRequest) {
        log.info("Iniciando proceso de autenticación para: {}", loginRequest.email());

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
                ))
                .doOnError(error -> log.error("Error durante el login: {}", error.getMessage(), error))
                .onErrorReturn(new LoginResponse(
                        null, null, null, null, null,
                        "Credenciales inválidas"
                ));
    }

    @Override
    public Mono<String> encodePassword(String plainPassword) {
        return passwordEncryption.encodePassword(plainPassword);
    }
}
