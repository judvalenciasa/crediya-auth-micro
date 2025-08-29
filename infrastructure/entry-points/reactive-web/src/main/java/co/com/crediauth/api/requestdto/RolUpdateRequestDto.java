package co.com.crediauth.api.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RolUpdateRequestDto(
        @NotBlank(message = "El nombre del rol no puede estar vacío")
        @Size(min = 2, max = 50, message = "El nombre del rol debe tener entre 2 y 50 caracteres")
        String name,

        @Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
        String description) {
}
