package co.com.crediauth.api;

import co.com.crediauth.api.errordto.ErrorResponseDto;
import co.com.crediauth.api.handler.GlobalExceptionHandler;
import co.com.crediauth.api.mapper.UserMapper;
import co.com.crediauth.api.requestdto.UserRequestDto;
import co.com.crediauth.usecase.user.InterfaceUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class Handler {

    private final InterfaceUserUseCase interfaceUserUseCase;
    private final UserMapper userMapper;
    private final GlobalExceptionHandler exceptionHandler;

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserRequestDto.class)
                .map(userMapper::toEntity)
                .flatMap(interfaceUserUseCase::saveUser)

                .map(userMapper::toDto)
                .flatMap(userResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userResponse))
                .onErrorResume(exceptionHandler::handleError);
    }
}
