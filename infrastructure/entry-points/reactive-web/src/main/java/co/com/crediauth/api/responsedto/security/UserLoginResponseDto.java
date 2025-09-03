package co.com.crediauth.api.responsedto.security;

public record UserLoginResponseDto (
        String accessToken,
        String email,
        String role,
        Long userId,
        String fullName
){
}
