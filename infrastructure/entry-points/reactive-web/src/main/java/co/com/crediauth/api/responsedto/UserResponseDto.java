package co.com.crediauth.api.responsedto;

public record UserResponseDto(

        String id,
        String names,
        String lastNames,
        String email,
        Double baseSalary,
        String birthDate,
        String address,
        String phone
)
{ }
