package co.com.crediauth.api;

import co.com.crediauth.api.globalerror.GlobalExceptionHandler;
import co.com.crediauth.api.mapper.AuthMapper;
import co.com.crediauth.api.requestdto.security.UserLoginRequestDto;
import co.com.crediauth.usecase.auth.IAuth;
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
    private final IAuth authUseCase;
    private final AuthMapper authMapper;
    private final GlobalExceptionHandler exceptionHandler;

    public Mono<ServerResponse> login(ServerRequest request) {
        log.info("event=AUTH_LOGIN_REQUEST_RECEIVED");

        return request.bodyToMono(UserLoginRequestDto.class)
                .flatMap(dto -> authUseCase.login(authMapper.toEntity(dto)))
                .map(authMapper::toDto)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .onErrorResume(exceptionHandler::handleError);
    }

    public Mono<ServerResponse> refreshToken(ServerRequest request) {
        log.info("event=REFRESH_TOKEN_REQUEST_RECEIVED");

        return request.bodyToMono(String.class)
                .flatMap(refreshToken -> authUseCase.refreshToken(refreshToken))
                .map(authMapper::toDto)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .onErrorResume(exceptionHandler::handleError);
    }


}
