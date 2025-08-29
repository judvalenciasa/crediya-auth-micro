package co.com.crediauth.api.requestdto.rol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RolUpdateRequestDto(
        @NotNull(message = "The name of the role cannot be empty")
        Long idRol,

        @NotBlank(message = "The name of the role is required")
        @Size(min = 2, max = 50, message = "The name must be between 2 and 50 characters")
        String name,

        @Size(max = 200, message = "The description must not exceed 200 characters")
        String description
) {
}
