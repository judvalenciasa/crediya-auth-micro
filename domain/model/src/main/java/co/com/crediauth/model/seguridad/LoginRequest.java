package co.com.crediauth.model.seguridad;


public record LoginRequest(
        String email,
        String password
) {
}
