package co.com.crediauth.api.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DocumentRequestDto(

        Long idTypeDocument,
        String documentNumber,
        String email
) {
}
