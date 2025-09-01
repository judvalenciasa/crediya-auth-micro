package co.com.crediauth.model.user.gateways;

import reactor.core.publisher.Mono;

public interface UserPasswordEncryptionGateway {
    Mono<String> encodePassword(String plainPassword);
}
