package co.com.crediauth.api.handler;

import co.com.crediauth.api.errordto.ErrorResponseDto;
import exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class GlobalExceptionHandler {

    public Mono<ServerResponse> handleError(Throwable error) {
        if (error instanceof BusinessException) {
            return handleBusinessException((BusinessException) error);
        } else if (error instanceof DuplicateKeyException) {
            return handleDuplicateKeyException((DuplicateKeyException) error);
        }
        return handleGenericError(error);
    }

    private Mono<ServerResponse> handleBusinessException(BusinessException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getMessage(),
                "BUS001",
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        );
        return ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .bodyValue(errorResponse);
    }

    private Mono<ServerResponse> handleDuplicateKeyException(DuplicateKeyException ex) {
        String message = "Error de duplicidad en base de datos";
        if (ex.getMessage().contains("users_email_key")) {
            message = "El correo electrónico ya está registrado en el sistema";
        }

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                message,
                "USR001",
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        );
        return ServerResponse
                .status(HttpStatus.CONFLICT)
                .bodyValue(errorResponse);
    }

    private Mono<ServerResponse> handleGenericError(Throwable ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "Ha ocurrido un error inesperado",
                "GEN001",
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        );
        return ServerResponse
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .bodyValue(errorResponse);
    }



}