package co.com.crediauth.usecase.user;

import co.com.crediauth.model.rol.gateways.RolRepository;
import co.com.crediauth.model.seguridad.gateways.AuthGateway;
import co.com.crediauth.model.user.User;
import co.com.crediauth.model.user.gateways.UserRepository;
import exception.BusinessException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase implements InterfaceUserUseCase {
    static final double SALARY_BASE_PERMITED = 15000000;
    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final AuthGateway authGateway;


    @Override
    public Mono<User> saveUser(User user) {
        return validateEmail(user)
                .then(validateDocument(user))
                .then(validateSalary(user))
                .then(validateRole(user))
                .then(processPassword(user))
                .then(userRepository.saveUser(user));
    }

    @Override
    public Mono<Boolean> documentIdExist(String documentId) {
        return userRepository.findByDocument(documentId).hasElement();
    }

    private Mono<Void> validateEmail(User user) {
        return userRepository.findByEmail(user.getEmail())
                .flatMap(existing -> Mono.error(new BusinessException("Email already exists: " + user.getEmail())))
                .switchIfEmpty(Mono.empty())
                .then();
    }

    private Mono<Void> validateDocument(User user) {
        return userRepository.findByDocument(user.getDocumentId())
                .flatMap(existing -> Mono.error(new BusinessException("Document already exists: " + user.getDocumentId())))
                .switchIfEmpty(Mono.empty())
                .then();
    }

    private Mono<Void> validateSalary(User user) {
        return Mono.just(user.getBaseSalary())
                .filter(salary -> salary <= SALARY_BASE_PERMITED)
                .switchIfEmpty(Mono.error(new BusinessException("Base salary cannot exceed " + SALARY_BASE_PERMITED)))
                .then();
    }

    private Mono<Void> validateRole(User user) {
        return rolRepository.findRolById(user.getRolId())
                .switchIfEmpty(Mono.error(new BusinessException("Role does not exist: " + user.getRolId())))
                .then();
    }

    private Mono<Void> processPassword(User user) {
        return authGateway.encodePassword(user.getDocumentId())
                .map(password -> {
                    user.setPassword(password);
                    user.setEnabled(true);
                    return user;
                })
                .then();
    }

}