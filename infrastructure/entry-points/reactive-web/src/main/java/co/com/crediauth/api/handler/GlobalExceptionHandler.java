package co.com.crediauth.api.handler;

import co.com.crediauth.api.errordto.ErrorResponseDto;
import co.com.crediauth.api.exception.ValidationException;
import exception.AdminException;
import exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class GlobalExceptionHandler {

    public Mono<ServerResponse> handleError(Throwable throwable) {
        if (throwable instanceof ValidationException) {
            return handleCustomValidationException((ValidationException) throwable);
        } else if (throwable instanceof BusinessException) {
            return handleBusinessException((BusinessException) throwable);
        } else if (throwable instanceof AdminException) {
            return handleAdminException((AdminException) throwable);
        } else {
            return handleGenericError(throwable);
        }
    }

    private Mono<ServerResponse> handleCustomValidationException(ValidationException ex) {
        String message = ex.getErrors().getAllErrors().get(0).getDefaultMessage();
        return buildErrorResponse(message, ErrorConstants.Codes.VALIDATION, HttpStatus.BAD_REQUEST);
    }

    private Mono<ServerResponse> handleBusinessException(BusinessException ex) {
        return buildErrorResponse(ex.getMessage(), ErrorConstants.Codes.BUSINESS, HttpStatus.BAD_REQUEST);
    }

    private Mono<ServerResponse> handleAdminException(AdminException ex) {
        return buildErrorResponse(ex.getMessage(), ErrorConstants.Codes.ADMIN, HttpStatus.BAD_REQUEST);
    }

    private Mono<ServerResponse> handleGenericError(Throwable ex) {
        String message = ErrorConstants.Messages.GENERIC_ERROR + ex.getMessage();
        return buildErrorResponse(message, ErrorConstants.Codes.GENERIC, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Mono<ServerResponse> buildErrorResponse(String message, String errorCode, HttpStatus status) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                message,
                errorCode,
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        );
        return ServerResponse
                .status(status)
                .bodyValue(errorResponse);
    }
}