package co.com.crediauth.usecase.user;

import co.com.crediauth.model.user.User;
import co.com.crediauth.model.user.gateways.UserRepository;
import exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase implements InterfaceUserUseCase {
    private final UserRepository userRepository;

    @Override
    public Mono<User> saveUser(User user) {

        return userRepository.existsByEmail(user.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new BusinessException("Email already exists"));
                    }
                    return userRepository.saveUser(user);
                });
    }



}
