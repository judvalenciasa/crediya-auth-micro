package co.com.crediauth.usecase.rol;

import co.com.crediauth.model.rol.Rol;
import reactor.core.publisher.Mono;

public interface IRolUseCase {
    Mono<Rol> createRol(Rol rol);
    Mono<Rol> updateRol(Rol rol);
    Mono<Boolean> deleteRol(Long idRol);
}
