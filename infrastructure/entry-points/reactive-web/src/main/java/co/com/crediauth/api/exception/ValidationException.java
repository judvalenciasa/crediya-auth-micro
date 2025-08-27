package co.com.crediauth.api.exception;

import org.springframework.validation.Errors;

public class ValidationException extends RuntimeException {
    private final Errors errors;

    public ValidationException(Errors errors) {
        super("Error de validación");
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}