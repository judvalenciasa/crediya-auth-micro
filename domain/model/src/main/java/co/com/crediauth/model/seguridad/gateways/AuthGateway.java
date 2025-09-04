package co.com.crediauth.model.seguridad.gateways;

import co.com.crediauth.model.seguridad.LoginResponse;
import co.com.crediauth.model.seguridad.TokenValidationResult;
import co.com.crediauth.model.user.User;
import reactor.core.publisher.Mono;

public interface AuthGateway {
    Mono<LoginResponse> generateToken(User user);
    Mono<Boolean>authenticateUser(String plainPassword, String userPassword);
    Mono<String>encodePassword(String plainPassword);

    Mono<TokenValidationResult> validateTokenAndExtractRole(String token);

}
