package co.com.crediauth.usecase.user;

import co.com.crediauth.model.rol.Rol;
import co.com.crediauth.model.rol.gateways.RolRepository;
import co.com.crediauth.model.user.User;
import co.com.crediauth.model.user.gateways.UserRepository;
import exception.BusinessException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase implements InterfaceUserUseCase {
    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    final static  String ROL_ID_DEFAULT = "CLIENT";
    final static double SALARY_BASE_PERMITED = 15000000;

    @Override
    public Mono<User> saveUser(User user) {
        return validateEmailNotExists(user.getEmail())
                .then(validateSalary(user.getBaseSalary()))
                .then(validateRoleExists(ROL_ID_DEFAULT))
                .flatMap(rol -> {
                    user.setRolId(rol.getIdRol());
                    return userRepository.saveUser(user);
                });
    }

    private Mono<Void> validateEmailNotExists(String email) {
        return userRepository.existsByEmail(email)
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new BusinessException(email + "already exists")))
                .then();
    }

    private Mono<Void> validateSalary(Double salary) {
        return Mono.just(salary)
                .filter(s -> s <= SALARY_BASE_PERMITED)
                .switchIfEmpty(Mono.error(new BusinessException("Base salary cannot exceed" + SALARY_BASE_PERMITED)))
                .then();
    }

    private Mono<Rol> validateRoleExists(String name) {
        return rolRepository.getRolByName(name)
                .switchIfEmpty(Mono.error(new BusinessException("Rol no encontrado con nombre: " + name)));
    }
}