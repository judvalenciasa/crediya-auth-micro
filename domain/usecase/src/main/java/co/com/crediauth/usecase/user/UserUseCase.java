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
    final static double SALARY_BASE_PERMITED = 15000000;

    @Override
    public Mono<User> saveUser(User user) {
        return emailExist(user.getEmail())
                .then(documentIdExist(user.getDocumentId()))
                .then(validateSalary(user.getBaseSalary()))
                .then(existRol(user.getRolId()))
                .then(userRepository.saveUser(user));
    }

    @Override
    public Mono<Boolean> documentIdExist(String documentId) {
        return userRepository.findByDocument(documentId)
                .hasElement();
    }

    private Mono<Boolean> emailExist(String email) {
        return userRepository.findByEmail(email)
                .hasElement();
    }

    private Mono<Void> validateSalary(Double salary) {
        return Mono.just(salary)
                .filter(baseSalary -> baseSalary <= SALARY_BASE_PERMITED)
                .switchIfEmpty(Mono.error(new BusinessException("Base salary cannot exceed" + SALARY_BASE_PERMITED)))
                .then();
    }

    private Mono<Boolean> existRol(Long rolId) {
        return rolRepository.findRolById(rolId).hasElement();
    }














}