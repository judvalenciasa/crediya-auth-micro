package co.com.crediauth.api;

import co.com.crediauth.api.exception.AuthenticationException;
import co.com.crediauth.api.globalerror.GlobalExceptionHandler;
import co.com.crediauth.api.mapper.AuthMapper;
import co.com.crediauth.api.requestdto.security.UserLoginRequestDto;
import co.com.crediauth.usecase.seguridad.ISecurityUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class AuthHandler {
        private final ISecurityUseCase iSecurityUseCase;
        private final AuthMapper authMapper;
        private final GlobalExceptionHandler exceptionHandler;

    public Mono<ServerResponse> login(ServerRequest request) {
        log.info("event=AUTH_LOGIN_REQUEST_RECEIVED");

        return request.bodyToMono(UserLoginRequestDto.class)
                .doOnNext(dto -> log.info("Login request para email: {}", dto.email()))
                .flatMap(dto -> iSecurityUseCase.login(authMapper.toEntity(dto)))
                .flatMap(loginResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(loginResponse))
                .onErrorResume(throwable -> {
                    if (throwable.getMessage().contains("Credenciales inválidas")) {
                        return ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new AuthenticationException(throwable.getMessage()) {
                                });
                    }
                    return exceptionHandler.handleError(throwable);
                });
    }
}
