package co.com.crediauth.usecase.user;

import co.com.crediauth.model.user.User;
import reactor.core.publisher.Mono;

public interface InterfaceUserUseCase {


    public Mono<User> saveUser(User user);

    public Mono<User> getUserById(Long id);

    Mono<Boolean> existsByEmail(String email);

}
