package co.com.crediauth.api;

import co.com.crediauth.api.exception.ValidationException;
import co.com.crediauth.api.globalerror.GlobalExceptionHandler;
import co.com.crediauth.api.mapper.UserMapper;
import co.com.crediauth.api.requestdto.user.UserCreateRequestDto;
import co.com.crediauth.api.responsedto.user.UserExistResponseDto;
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

import java.util.Map;


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
        return serverRequest.bodyToMono(UserCreateRequestDto.class)
                .flatMap(dto -> {
                    Errors errors = new BeanPropertyBindingResult(dto, UserCreateRequestDto.class.getName());
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
        String documentNumber = serverRequest.pathVariable("documentNumber");
        log.info("event=USER_EXISTENCE_CHECK_INITIATED, documentNumber={}", documentNumber);

        return interfaceUserUseCase.documentIdExist(documentNumber)
                .doOnNext(exists -> log.info("event=USER_EXISTENCE_CHECK_RESULT, documentNumber={}, exists={}", documentNumber, exists))
                .map(exists -> Map.of(
                        "exists", exists,
                        "documentNumber", documentNumber,
                        "message", exists ? "Usuario encontrado" : "Usuario no encontrado"
                ))
                .doOnNext(response -> log.info("event=USER_EXISTENCE_RESPONSE_GENERATED, response={}", response))
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .doOnSuccess(response -> log.info("event=USER_EXISTENCE_CHECK_COMPLETED, documentNumber={}", documentNumber))
                .onErrorResume(throwable -> {
                    log.error("event=USER_EXISTENCE_CHECK_ERROR, documentNumber={}, error={}", documentNumber, throwable.getMessage(), throwable);
                    return exceptionHandler.handleError(throwable);
                });
    }

}
