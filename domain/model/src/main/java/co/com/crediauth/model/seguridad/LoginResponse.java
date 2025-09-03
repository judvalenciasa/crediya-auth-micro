package co.com.crediauth.model.seguridad;

public record LoginResponse(
        String accessToken,
        String email,
        String role,
        Long userId,
        String fullName,
        String message
) {
}
