package co.com.crediauth.r2dbc;

import co.com.crediauth.model.user.User;
import co.com.crediauth.model.user.gateways.UserRepository;
import co.com.crediauth.r2dbc.entity.UserEntity;
import co.com.crediauth.r2dbc.exception.HandleDatabaseError;
import co.com.crediauth.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        Long,
        UserReactiveRepository
        > implements UserRepository {
    private final TransactionalOperator transactionalOperator;
    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {

        super(repository, mapper, d -> mapper.map(d, User.class/* change for domain model */));
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<User> saveUser(User user) {
        return Mono.just(user)
                .map(u -> mapper.map(u, UserEntity.class))
                .flatMap(repository::save)
                .map(savedEntity -> mapper.map(savedEntity, User.class))
                .onErrorMap(error -> new HandleDatabaseError("Error guardando usuario: " + error.getMessage()))
                .as(transactionalOperator::transactional);
    }

    @Override
    public Mono<User> findByDocument(String documentId) {
        return repository.findByDocumentId(documentId)
                .map(user->mapper.map(user, User.class))
                .onErrorMap(error -> new HandleDatabaseError("Error verificando documento: " + error.getMessage()));
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(user->mapper.map(user, User.class))
                .onErrorMap(error -> new HandleDatabaseError("Error verificando documento: " + error.getMessage()));
    }



}
