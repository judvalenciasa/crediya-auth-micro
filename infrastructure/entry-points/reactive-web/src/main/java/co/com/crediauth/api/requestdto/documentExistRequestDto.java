package co.com.crediauth.api.requestdto;

public record documentExistRequestDto(

        Long idTypeDocument,
        String documentNumber,


        String email
) {
}
