package co.com.crediauth.r2dbc;

import co.com.crediauth.model.rol.Rol;
import co.com.crediauth.model.rol.gateways.RolRepository;
import co.com.crediauth.r2dbc.entities.RolEntity;
import co.com.crediauth.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class RolReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Rol/* change for domain model */,
        RolEntity/* change for adapter model */,
        Long,
        RolReactiveRepository
> implements RolRepository {
    public RolReactiveRepositoryAdapter(RolReactiveRepository repository, ObjectMapper mapper) {

        super(repository, mapper, d -> mapper.map(d, Rol.class/* change for domain model */));
    }

    @Override
    public Mono<Boolean> existsByidRol(Long idRol) {
        return repository.existsById(idRol);
    }

    @Override
    public Mono<Rol> saveRol(Rol rol) {
        return save(rol);
    }

    @Override
    public Mono<Void> deleteRol(Long idRol) {
        return null;
    }

    @Override
    public Mono<Void> getRolById(Long idRol) {
        return getRolById(idRol);
    }
}
