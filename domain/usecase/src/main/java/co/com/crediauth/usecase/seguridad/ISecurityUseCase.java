package co.com.crediauth.usecase.seguridad;

import co.com.crediauth.model.seguridad.LoginRequest;
import co.com.crediauth.model.seguridad.LoginResponse;
import reactor.core.publisher.Mono;

public interface ISecurityUseCase {
    Mono<LoginResponse> login(LoginRequest loginRequest);
    Mono<Boolean> isTokenValidAndHasAccess(String token, String path, String method);


}
