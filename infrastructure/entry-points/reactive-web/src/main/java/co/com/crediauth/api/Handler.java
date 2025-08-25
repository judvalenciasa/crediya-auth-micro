package co.com.crediauth.api;

import co.com.crediauth.model.user.User;
import co.com.crediauth.usecase.user.InterfaceUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final InterfaceUserUseCase interfaceUserUseCase;

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(User.class)
                .flatMap(dato -> interfaceUserUseCase.saveUser(dato))
                .flatMap(userCreate -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON).bodyValue(userCreate));
    }
}
