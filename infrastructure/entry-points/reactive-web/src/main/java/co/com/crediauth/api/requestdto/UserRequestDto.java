package co.com.crediauth.api.requestdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Date;

public record UserRequestDto(
        @NotBlank(message = "Names cannot be empty")
        String names,

        @NotBlank(message = "Last names cannot be empty")
        String lastNames,

        @Email(message = "Invalid email format")
        @NotBlank(message = "Email cannot be empty")
        String email,

        @NotNull(message = "Base salary is required")
        @Min(value = 0, message = "Base salary must be greater or equal to 0")
        @Max(value = 15000000, message = "Base salary must not exceed 15,000,000")
        Double baseSalary,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthDate,
        String address,
        String phone,

        @NotBlank(message = "Document type cannot be empty")
        String documentId
) {
}


