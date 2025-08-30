package co.com.crediauth.r2dbc;

import co.com.crediauth.model.rol.Rol;
import co.com.crediauth.model.rol.gateways.RolRepository;
import co.com.crediauth.r2dbc.entity.RolEntity;
import co.com.crediauth.r2dbc.exception.HandleDatabaseError;
import co.com.crediauth.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Repository
public class RolReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Rol,
        RolEntity,
        Long,
        RolReactiveRepository
> implements RolRepository {
    private final TransactionalOperator transactionalOperator;
    public RolReactiveRepositoryAdapter(RolReactiveRepository repository, ObjectMapper mapper,TransactionalOperator transactionalOperator) {

        super(repository, mapper, d -> mapper.map(d, Rol.class/* change for domain model */));
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<Boolean> existsByidRol(Long idRol) {
        return repository.existsById(idRol)
                .onErrorMap(error -> new HandleDatabaseError("Error verificando rol: " + error.getMessage()));
    }

    @Override
    public Mono<Rol> saveRol(Rol rol) {
        return transactionalOperator.transactional(save(rol)
                .onErrorMap(error -> new HandleDatabaseError("Error guardando rol: " + error.getMessage())));
    }

    @Override
    public Mono<Void> deleteRol(Long idRol) {
        return transactionalOperator.transactional(repository.findById(idRol)
                .flatMap(rolEntity -> repository.deleteById(idRol))
                .onErrorMap(error -> new HandleDatabaseError("Error eliminando rol: " + error.getMessage()))
                .then());
    }

    @Override
    public Mono<Boolean> getRolById(Long idRol) {
        return repository.existsById(idRol)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.error(new HandleDatabaseError("Rol no encontrado con id: " + idRol));
                    }
                    return Mono.just(true);
                })
                .onErrorMap(error ->
                        new HandleDatabaseError("Error buscando rol por ID: " + error.getMessage())
                );
    }
}
