package co.com.crediauth.r2dbc;

import co.com.crediauth.r2dbc.entities.RolEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface RolReactiveRepository extends ReactiveCrudRepository<RolEntity, Long>, ReactiveQueryByExampleExecutor<RolEntity> {
    Mono<RolEntity> findByName(String name);
}
