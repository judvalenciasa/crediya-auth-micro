package co.com.crediauth.usecase.user;

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
    final static  Long ROL_ID_DEFAULT = 1l;
    final static  double SALARY_BASE_PERMITED = 15000000;

    @Override
    public Mono<User> saveUser(User user) {
        return Mono.when(
                validateEmailNotExists(user.getEmail()),
                validateSalary(user.getBaseSalary()),
                validateRoleExists(ROL_ID_DEFAULT)
        ).then(Mono.defer(() -> {
            user.setRolId(ROL_ID_DEFAULT);
            return userRepository.saveUser(user);
        }));
    }


    private Mono<Void> validateEmailNotExists(String email) {
        return userRepository.existsByEmail(email)
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new BusinessException("Email already exists")))
                .then();
    }

    private Mono<Void> validateSalary(Double salary) {
        return Mono.just(salary)
                .filter(s -> s <= SALARY_BASE_PERMITED)
                .switchIfEmpty(Mono.error(new BusinessException("Base salary cannot exceed 15000000")))
                .then();
    }

    private Mono<Void> validateRoleExists(Long roleId) {
        return rolRepository.existsByidRol(roleId)
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new BusinessException("Default role does not exist")))
                .then();
    }




}
