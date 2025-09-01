package co.com.crediauth.model.authtoken;

import java.time.LocalDateTime;

public class AuthToken {
    private String accessToken;
    private String refreshToken;
    private LocalDateTime accessTokenExpiration;
    private LocalDateTime refreshTokenExpiration;
    private Long userId;
    private String email;
    private String role;


    public AuthToken(){

    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public void setAccessTokenExpiration(LocalDateTime accessTokenExpiration) {
        this.accessTokenExpiration = accessTokenExpiration;
    }

    public LocalDateTime getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    public void setRefreshTokenExpiration(LocalDateTime refreshTokenExpiration) {
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
