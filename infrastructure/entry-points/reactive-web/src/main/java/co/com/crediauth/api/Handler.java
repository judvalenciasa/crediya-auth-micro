package co.com.crediauth.api;

import co.com.crediauth.api.errordto.ErrorResponseDto;
import co.com.crediauth.api.exception.ValidationException;
import co.com.crediauth.api.handler.GlobalExceptionHandler;
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

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



@Component
@RequiredArgsConstructor
public class Handler {

    private final InterfaceUserUseCase interfaceUserUseCase;
    private final UserMapper userMapper;
    private final GlobalExceptionHandler exceptionHandler;
    private final Validator validator;

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserRequestDto.class)
                .flatMap(dto -> {
                    Errors errors = new BeanPropertyBindingResult(dto, UserRequestDto.class.getName());
                    validator.validate(dto, errors);

                    if (errors.hasErrors()) {
                        return Mono.error(new ValidationException(errors));
                    }

                    return interfaceUserUseCase.saveUser(userMapper.toEntity(dto));
                })
                .map(userMapper::toDto)
                .flatMap(userResponse ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(userResponse)
                )
                .onErrorResume(exceptionHandler::handleError);

    }
}
