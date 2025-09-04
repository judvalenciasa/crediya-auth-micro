package co.com.crediauth.usecase.seguridad;

import co.com.crediauth.model.seguridad.LoginRequest;
import co.com.crediauth.model.seguridad.LoginResponse;
import co.com.crediauth.model.seguridad.gateways.AuthGateway;
import co.com.crediauth.model.user.gateways.UserRepository;
import exception.BusinessException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SecurityUseCase implements ISecurityUseCase{
    private final AuthGateway authGateway;
    private final UserRepository userRepository;

    @Override
    public Mono<LoginResponse> login(LoginRequest loginRequest) {
        return userRepository.findByEmail(loginRequest.email())
                .switchIfEmpty(Mono.error(new BusinessException("Usuario no encontrado al intentar logear")))
                .flatMap(user ->
                        authGateway.authenticateUser(loginRequest.password(), user.getPassword())
                                .flatMap(valid -> {
                                    if (valid) {
                                        return authGateway.generateToken(user);
                                    } else {
                                        return Mono.error(new BusinessException("Credenciales inválidas"));
                                    }
                                })
                );
    }



}
