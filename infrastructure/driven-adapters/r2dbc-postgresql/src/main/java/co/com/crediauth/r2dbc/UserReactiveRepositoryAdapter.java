package co.com.crediauth.r2dbc;

import co.com.crediauth.model.user.User;
import co.com.crediauth.model.user.gateways.UserRepository;
import co.com.crediauth.r2dbc.entities.UserEntity;
import co.com.crediauth.r2dbc.exception.HandleDatabaseError;
import co.com.crediauth.r2dbc.helper.ReactiveAdapterOperations;
import co.com.crediauth.r2dbc.mapper.UserEntityMapper;
import lombok.extern.slf4j.Slf4j;
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
        UserEntity userEntity = UserEntityMapper.toEntity(user);
        return transactionalOperator.transactional(repository.save(userEntity)
                .map(UserEntityMapper::toUser));
    }


    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Mono<Boolean> existsByDocumentId(String documentId) {
        return repository.existsByDocumentId(documentId)
                .onErrorMap(error -> new HandleDatabaseError("Error verificando documento: " + error.getMessage()));
    }
}
