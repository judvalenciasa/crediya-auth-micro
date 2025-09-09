package co.com.crediauth.model.user.gateways;

import co.com.crediauth.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> saveUser(User user) ;
    Mono<User> findByEmail(String email);
    Mono<User> findByDocument(String document);
    Mono<User> findById(Long idUser);


    Mono<User> updateUser(User user);



}

