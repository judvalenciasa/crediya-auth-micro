package co.com.crediauth.api;

import co.com.crediauth.api.exception.ValidationException;
import co.com.crediauth.api.handler.GlobalExceptionHandler;
import co.com.crediauth.api.mapper.RolMapper;
import co.com.crediauth.api.requestdto.RolUpdateRequestDto;
import co.com.crediauth.usecase.rol.IRolUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RolHandler {

    private final IRolUseCase irolUseCase;
    private final Validator validator;
    private final RolMapper rolMapper;
    private final GlobalExceptionHandler exceptionHandler;


    public Mono<ServerResponse> createRol(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RolUpdateRequestDto.class)
                .flatMap(dto -> {
                    Errors errors = new BeanPropertyBindingResult(dto, RolUpdateRequestDto.class.getName());
                    validator.validate(dto, errors);

                    if (errors.hasErrors()) {
                        return Mono.error(new ValidationException(errors));
                    }

                    return irolUseCase.createRol(rolMapper.toEntity(dto));
                })
                .map(rolMapper::toDto)
                .flatMap(rolResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(rolResponse))
                .onErrorResume(exceptionHandler::handleError);
    }

    public Mono<ServerResponse> updateRol(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RolUpdateRequestDto.class)
                .flatMap(dto -> {
                    Errors errors = new BeanPropertyBindingResult(dto, RolUpdateRequestDto.class.getName());
                    validator.validate(dto, errors);

                    if (errors.hasErrors()) {
                        return Mono.error(new ValidationException(errors));
                    }

                    return irolUseCase.updateRol(rolMapper.toEntity(dto));
                })
                .map(rolMapper::toDto)
                .flatMap(rolResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(rolResponse))
                .onErrorResume(exceptionHandler::handleError);
    }

    public Mono<ServerResponse> deleteRol(ServerRequest serverRequest) {
        String idRol = serverRequest.pathVariable("idRol");

        return irolUseCase.deleteRol(Long.valueOf(idRol))
                .then(ServerResponse.noContent().build())
                .onErrorResume(exceptionHandler::handleError);
    }

}
