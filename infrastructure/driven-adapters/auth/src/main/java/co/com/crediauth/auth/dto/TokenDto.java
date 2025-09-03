package co.com.crediauth.auth.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class TokenDto {
    private String accessToken;
    private LocalDateTime accessTokenExpiration;
    private String email;
    private Long role;
    private Long userId;
    private String fullName;

    public TokenDto(String accessToken, LocalDateTime accessTokenExpiration, String email, Long role, Long userId, String fullName) {
        this.accessToken = accessToken;
        this.accessTokenExpiration = accessTokenExpiration;
        this.email = email;
        this.role = role;
        this.userId = userId;
        this.fullName = fullName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
