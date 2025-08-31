package co.com.crediauth.r2dbc;

import co.com.crediauth.model.rol.Rol;
import co.com.crediauth.r2dbc.entity.RolEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface RolReactiveRepository extends ReactiveCrudRepository<RolEntity, Long>, ReactiveQueryByExampleExecutor<RolEntity> {
    Mono<RolEntity> findByIdRol(Long idRol);
}
