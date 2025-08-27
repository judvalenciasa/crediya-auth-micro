package co.com.crediauth.api.handler;

import co.com.crediauth.api.errordto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Mono<ServerResponse> handleGenericError(Exception ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        );

        return ServerResponse
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .bodyValue(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ServerResponse> handleValidationError(IllegalArgumentException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        );

        return ServerResponse
                .status(HttpStatus.BAD_REQUEST)
                .bodyValue(errorResponse);
    }
}