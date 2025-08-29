package co.com.crediauth.api.requestdto.rol;

import jakarta.validation.constraints.NotBlank;

public record RolDeleteRequestDto(
        @NotBlank(message = "the id is required")
        Long id
) {
}
