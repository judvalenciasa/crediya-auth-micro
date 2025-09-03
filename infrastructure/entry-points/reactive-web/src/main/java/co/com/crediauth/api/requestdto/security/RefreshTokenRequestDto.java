package co.com.crediauth.api.requestdto.security;


import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDto(
        @NotBlank(message = "The refreshToken is requerited")
        String refreshToken
) {
}
