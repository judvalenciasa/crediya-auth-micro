package co.com.crediauth.api.errordto;

public record ErrorResponseDto(
        String message,
        String code,
        String timestamp
) {}
