package co.com.crediauth.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.authorization")
public class JwtAuthenticationConfig {
    private List<AuthorizationRule> rules;

    @Data
    public static class AuthorizationRule {
        private String path;
        private List<String> methods;
        private List<String> roles;
    }
}
