package co.com.crediauth.model.rol.gateways;

import co.com.crediauth.model.rol.Rol;
import reactor.core.publisher.Mono;

public interface RolRepository {
    Mono<Rol> saveRol(Rol rol);
    Mono<Boolean> deleteRol(Long idRol);
    Mono<Rol>findRolById(Long idRol);
}
