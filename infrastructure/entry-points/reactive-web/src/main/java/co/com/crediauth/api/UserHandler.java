package co.com.crediauth.api;

import co.com.crediauth.api.exception.ValidationException;
import co.com.crediauth.api.globalerror.GlobalExceptionHandler;
import co.com.crediauth.api.mapper.UserMapper;
import co.com.crediauth.api.requestdto.UserRequestDto;
import co.com.crediauth.usecase.user.InterfaceUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserHandler {

    private final InterfaceUserUseCase interfaceUserUseCase;
    private final UserMapper userMapper;
    private final GlobalExceptionHandler exceptionHandler;

    private final Validator validator;

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        log.info("event=USER_CREATION_INITIATED");
        return serverRequest.bodyToMono(UserRequestDto.class)
                .flatMap(dto -> {
                    Errors errors = new BeanPropertyBindingResult(dto, UserRequestDto.class.getName());
                    validator.validate(dto, errors);

                    if (errors.hasErrors()) {
                        return Mono.error(new ValidationException(errors));
                    }

                    log.info("Use case starting");
                    return interfaceUserUseCase.saveUser(userMapper.toEntity(dto));
                })
                .doOnNext(savedUser -> log.info("event=USER_SAVED_SUCCESSFULLY, user={}", savedUser))
                .map(userMapper::toDto)
                .doOnNext(responseDto -> log.info("event=RESPONSE_DTO_GENERATED, response={}", responseDto))
                .flatMap(userResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userResponse))
                .doOnSuccess(response -> log.info("event=USER_CREATION_COMPLETED"))
                .onErrorResume(exceptionHandler::handleError);
    }


    public Mono<ServerResponse> existUserByDocumentNumber(ServerRequest serverRequest) {
        return null;
    }

}
