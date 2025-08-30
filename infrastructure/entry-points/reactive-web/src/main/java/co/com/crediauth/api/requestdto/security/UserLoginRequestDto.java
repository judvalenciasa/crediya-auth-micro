package co.com.crediauth.api.requestdto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequestDto(
        @Email(message = "The email must be valid")
        @NotBlank(message = "The email is required")
        String email,

        @NotBlank(message = "The password is required")
        String password
) {
}
