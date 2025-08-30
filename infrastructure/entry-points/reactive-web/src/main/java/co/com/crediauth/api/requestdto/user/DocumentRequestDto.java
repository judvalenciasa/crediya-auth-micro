package co.com.crediauth.api.requestdto.user;

public record DocumentRequestDto(
        Long idTypeDocument,
        String documentNumber,
        String email
) {
}
