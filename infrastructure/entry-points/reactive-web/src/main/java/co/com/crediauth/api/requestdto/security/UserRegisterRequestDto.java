package co.com.crediauth.api.requestdto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterRequestDto(
        @NotBlank(message = "Los nombres son requeridos")
        String names,

        @NotBlank(message = "Los apellidos son requeridos")
        String lastNames,

        @Email(message = "Email inválido")
        @NotBlank(message = "El email es requerido")
        String email,

        @NotBlank(message = "La contraseña es requerida")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password,

        @NotBlank(message = "El documento de identidad es requerido")
        String documentId
) {
}
