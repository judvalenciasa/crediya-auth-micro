package co.com.crediauth.usecase.user;

import co.com.crediauth.model.user.User;
import reactor.core.publisher.Mono;

public interface InterfaceUserUseCase {
    Mono<User> saveUser(User user) ;
    Mono<Boolean> documentIdExist(String documentId);
}
