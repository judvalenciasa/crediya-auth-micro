package co.com.crediauth.usecase.seguridad;

import co.com.crediauth.model.seguridad.LoginRequest;
import co.com.crediauth.model.seguridad.LoginResponse;
import co.com.crediauth.model.seguridad.gateways.AuthGateway;
import co.com.crediauth.model.user.gateways.UserRepository;
import exception.BusinessException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SecurityUseCase implements ISecurityUseCase{
    private static final int CANTIDAD_INTENTOS = 3;
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
                                        user.setLoginAttempts(CANTIDAD_INTENTOS);
                                        return userRepository.updateUser(user)
                                                .then(authGateway.generateToken(user));
                                    } else {
                                        int currentAttempts = user.getLoginAttempts();
                                        if (currentAttempts <= 1) {
                                            return Mono.error(new BusinessException("Usuario bloqueado por exceso de intentos fallidos"));
                                        }
                                        user.setLoginAttempts(currentAttempts - 1);
                                        return userRepository.updateUser(user)
                                                .then(Mono.error(new BusinessException("Credenciales inválidas. Intentos restantes: " + (currentAttempts - 1))));
                                    }
                                })
                );
    }

    @Override
    public Mono<Boolean> isTokenValidAndHasAccess(String token, String path, String method) {
        return authGateway.validateTokenAndExtractRole(token)
                .flatMap(result -> {
                    if (!result.getValid()) {
                        return Mono.just(false);
                    }

                    return hasRoleAccess(result.getRole(), path, method);
                })
                .onErrorResume(throwable -> {
                    return Mono.just(false);
                });
    }

    private Mono<Boolean> hasRoleAccess(Long role, String path, String method) {
        Long administrador = 21L;
        Long asesor = 23L;
        Long cliente = 22L;

        return Mono.fromCallable(() -> {
            Map<String, List<Long>> rules = Map.of(
                    "/api/v1/users:POST", List.of(administrador, asesor),
                    "/api/v1/reports:GET", List.of(administrador),
                    "/api/v1/roles:*", List.of(administrador),
                    "/api/v1/requests:POST", List.of(cliente),
                    "/api/v1/requests:GET", List.of(asesor),
                    "/api/v1/requests:PUT", List.of(asesor)
            );

            String key = path + ":" + method;

            if (rules.containsKey(key)) {
                return rules.get(key).contains(role);
            }

            for (Map.Entry<String, List<Long>> entry : rules.entrySet()) {
                if (entry.getKey().startsWith(path + ":") && entry.getKey().endsWith(":*")) {
                    return entry.getValue().contains(role);
                }
            }

            return false;
        });
    }



}
