package co.com.crediauth.api;

import co.com.crediauth.api.exception.ValidationException;
import co.com.crediauth.api.globalerror.GlobalExceptionHandler;
import co.com.crediauth.api.mapper.RolMapper;
import co.com.crediauth.api.requestdto.rol.RolCreateRequestDto;
import co.com.crediauth.api.requestdto.rol.RolUpdateRequestDto;
import co.com.crediauth.usecase.rol.IRolUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class RolHandler {

    private final IRolUseCase irolUseCase;
    private final Validator validator;
    private final RolMapper rolMapper;
    private final GlobalExceptionHandler exceptionHandler;


    public Mono<ServerResponse> createRol(ServerRequest serverRequest) {
        log.info("event=ROL_CREATION_INITIATED");
        return serverRequest.bodyToMono(RolCreateRequestDto.class)
                .flatMap(dto -> {
                    log.info("event=ROL_VALIDATION_STARTED, dto={}", dto);
                    Errors errors = new BeanPropertyBindingResult(dto, RolCreateRequestDto.class.getName());
                    validator.validate(dto, errors);

                    if (errors.hasErrors()) {
                        log.error("event=ROL_VALIDATION_FAILED, errors={}", errors.getAllErrors());
                        return Mono.error(new ValidationException(errors));
                    }

                    log.info("event=ROL_USE_CASE_STARTING");
                    return irolUseCase.createRol(rolMapper.toEntity(dto));
                })
                .doOnNext(savedRol -> log.info("event=ROL_SAVED_SUCCESSFULLY, rol={}", savedRol))
                .map(rolMapper::toDto)
                .doOnNext(responseDto -> log.info("event=ROL_RESPONSE_DTO_GENERATED, response={}", responseDto))
                .flatMap(rolResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(rolResponse))
                .doOnSuccess(response -> log.info("event=ROL_CREATION_COMPLETED"))
                .onErrorResume(exceptionHandler::handleError);
    }

    public Mono<ServerResponse> updateRol(ServerRequest serverRequest) {
        log.info("event=ROL_UPDATE_INITIATED");
        return serverRequest.bodyToMono(RolUpdateRequestDto.class)
                .flatMap(dto -> {
                    log.info("event=ROL_UPDATE_VALIDATION_STARTED, dto={}", dto);
                    Errors errors = new BeanPropertyBindingResult(dto, RolUpdateRequestDto.class.getName());
                    validator.validate(dto, errors);

                    if (errors.hasErrors()) {
                        log.error("event=ROL_UPDATE_VALIDATION_FAILED, errors={}", errors.getAllErrors());
                        return Mono.error(new ValidationException(errors));
                    }

                    log.info("event=ROL_UPDATE_USE_CASE_STARTING");
                    return irolUseCase.updateRol(rolMapper.toEntity(dto));
                })
                .doOnNext(updatedRol -> log.info("event=ROL_UPDATED_SUCCESSFULLY, rol={}", updatedRol))
                .map(rolMapper::toDto)
                .doOnNext(responseDto -> log.info("event=ROL_UPDATE_RESPONSE_DTO_GENERATED, response={}", responseDto))
                .flatMap(rolResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(rolResponse))
                .doOnSuccess(response -> log.info("event=ROL_UPDATE_COMPLETED"))
                .onErrorResume(exceptionHandler::handleError);
    }

    public Mono<ServerResponse> deleteRol(ServerRequest serverRequest) {
        String idRol = serverRequest.pathVariable("idRol");
        log.info("event=ROL_DELETION_INITIATED, idRol={}", idRol);

        return irolUseCase.deleteRol(Long.valueOf(idRol))
                .doOnSuccess(v -> log.info("event=ROL_DELETED_SUCCESSFULLY, idRol={}", idRol))
                .then(ServerResponse.noContent().build())
                .doOnSuccess(response -> log.info("event=ROL_DELETION_COMPLETED, idRol={}", idRol))
                .onErrorResume(exceptionHandler::handleError);
    }

}
