package co.com.crediauth.usecase.user;

import co.com.crediauth.model.user.User;
import co.com.crediauth.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase implements InterfaceUserUseCase{
    private final UserRepository userRepositorio;

    @Override
    public Mono<User> saveUser(User user) {
        return userRepositorio.saveUser(user);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return userRepositorio.existsByEmail(email);
    }
}
