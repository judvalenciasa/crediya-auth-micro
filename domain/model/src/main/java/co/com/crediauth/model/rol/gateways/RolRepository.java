package co.com.crediauth.model.rol.gateways;

import co.com.crediauth.model.rol.Rol;
import reactor.core.publisher.Mono;

public interface RolRepository {
    Mono<Boolean> existsByidRol(Long idRol);
    Mono<Rol> saveRol(Rol rol);
    Mono<Void> deleteRol(Long idRol);
    Mono<Void> getRolById(Long idRol);
    Mono<Rol> getRolByName(String name);
}
