package co.com.crediauth.api.responsedto;

import java.time.LocalDate;

public record UserResponseDto(

        String id,
        String names,
        String lastNames,
        String email,
        Double baseSalary,
        LocalDate birthDate,
        String address,
        String phone,
        String documentId,
        String rolId
)
{ }
