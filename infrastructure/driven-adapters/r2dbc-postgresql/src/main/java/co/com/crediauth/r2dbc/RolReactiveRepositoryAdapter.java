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
        super(repository, mapper, d -> mapper.map(d, Rol.class));
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<Rol> saveRol(Rol rol) {
        return transactionalOperator.transactional(save(rol)
                .onErrorMap(error -> new HandleDatabaseError("Error guardando rol: " + error.getMessage())));
    }

    @Override
    public Mono<Boolean> deleteRol(Long idRol) {
        return transactionalOperator.transactional(
                repository.findById(idRol)
                        .switchIfEmpty(Mono.error(new HandleDatabaseError("Rol no encontrado con id: " + idRol)))
                        .flatMap(rol -> repository.deleteById(idRol)
                                .thenReturn(true)
                        )
                        .onErrorMap(error -> new HandleDatabaseError("Error eliminando rol: " + error.getMessage()))
        );
    }

    @Override
    public Mono<Rol> findRolById(Long idRol) {
        return repository.findByIdRol(idRol)
                .map(rol->mapper.map(rol, Rol.class))
                .onErrorMap(error -> new HandleDatabaseError("Error buscando rol: " + error.getMessage()));
    }
}
