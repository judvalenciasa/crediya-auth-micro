package co.com.crediauth.api.responsedto.user;

public record UserExistResponseDto(
        Boolean exists,
        String documentId,
        String message
) {
    public static UserExistResponseDto of(Boolean exists, String documentId) {
        String message = exists ? "Usuario encontrado" : "Usuario no encontrado";
        return new UserExistResponseDto(exists, documentId, message);
    }
}