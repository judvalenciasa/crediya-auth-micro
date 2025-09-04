package co.com.crediauth.model.seguridad;

import java.time.LocalDateTime;


public class LoginResponse {
    private String accessToken;
    private LocalDateTime accessTokenExpiration;
    private Long role;

    public LoginResponse(String accessToken, LocalDateTime accessTokenExpiration, Long role) {
        this.accessToken = accessToken;
        this.accessTokenExpiration = accessTokenExpiration;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LocalDateTime getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public void setAccessTokenExpiration(LocalDateTime accessTokenExpiration) {
        this.accessTokenExpiration = accessTokenExpiration;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }
}