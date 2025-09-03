package co.com.crediauth.usecase.user;

import co.com.crediauth.model.rol.Rol;
import co.com.crediauth.model.rol.gateways.RolRepository;
import co.com.crediauth.model.user.User;
import co.com.crediauth.model.user.gateways.UserRepository;
import co.com.crediauth.usecase.seguridad.ISecurityUseCase;
import exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static co.com.crediauth.usecase.user.UserUseCase.SALARY_BASE_PERMITED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private ISecurityUseCase iSecurityUseCase;

    @InjectMocks
    private UserUseCase userUseCase;

    @Test
    void When_UserInformationIsCorrect_Expect_UserToBeSavedCorrectly() {
        // Arrange
        User user = UserBuilder.aValidUser().rolId(11L).build();
        User savedUser = UserBuilder.aValidUser().id(1L).rolId(11L).build();

        when(userRepository.findByEmail("juan.perez@email.com")).thenReturn(Mono.empty());
        when(userRepository.findByDocument("12345678")).thenReturn(Mono.empty());
        when(rolRepository.findRolById(11L)).thenReturn(Mono.just(new Rol()));
        when(iSecurityUseCase.encodePassword("12345678")).thenReturn(Mono.just("encodedPassword"));
        when(userRepository.saveUser(any(User.class))).thenReturn(Mono.just(savedUser));

        // Act
        Mono<User> result = userUseCase.saveUser(user);

        // Assert
        StepVerifier.create(result)
                .expectNext(savedUser)
                .verifyComplete();


        verify(userRepository).findByEmail("juan.perez@email.com");
        verify(userRepository).findByDocument("12345678");
        verify(rolRepository).findRolById(11L);
        verify(iSecurityUseCase).encodePassword("12345678");
        verify(userRepository).saveUser(user);
    }

    @Test
    void When_EmailAlreadyExists_Expect_BusinessException() {
        // Arrange
        User user = UserBuilder.aValidUser().rolId(11L).build();
        User existingUser = UserBuilder.aValidUser().id(999L).rolId(11L).build();

        when(userRepository.findByEmail("juan.perez@email.com")).thenReturn(Mono.just(existingUser));
        when(userRepository.findByDocument("12345678")).thenReturn(Mono.empty());
        when(rolRepository.findRolById(11L)).thenReturn(Mono.just(new Rol()));
        when(iSecurityUseCase.encodePassword("12345678")).thenReturn(Mono.just("encodedPassword"));
        when(userRepository.saveUser(any(User.class))).thenReturn(Mono.just(user));

        // Act
        Mono<User> result = userUseCase.saveUser(user);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof BusinessException &&
                                throwable.getMessage().contains("Email already exists")
                )
                .verify();

    }

        @Test
        void When_DocumentAlreadyExists_Expect_BusinessException() {
            // Arrange
            User user = UserBuilder.aValidUser().rolId(11L).build();
            User existingUser = UserBuilder.aValidUser().id(999L).rolId(11L).build();

            when(userRepository.findByEmail("juan.perez@email.com")).thenReturn(Mono.empty());
            when(userRepository.findByDocument("12345678")).thenReturn(Mono.just(existingUser));
            when(rolRepository.findRolById(11L)).thenReturn(Mono.just(new Rol()));
            when(iSecurityUseCase.encodePassword("12345678")).thenReturn(Mono.just("encodedPassword"));
            when(userRepository.saveUser(any(User.class))).thenReturn(Mono.just(user));

            // Act
            Mono<User> result = userUseCase.saveUser(user);

            // Assert
            StepVerifier.create(result)
                    .expectErrorMatches(throwable ->
                            throwable instanceof BusinessException &&
                                    throwable.getMessage().contains("Document already exists")
                    )
                    .verify();
        }

        @Test
        void When_BaseSalaryGreaterThan_Expect_BusinessException() {
            // Arrange
            User user = UserBuilder.aHighSalaryUser().rolId(11L).build();

            when(userRepository.findByEmail("juan.perez@email.com")).thenReturn(Mono.empty());
            when(userRepository.findByDocument("12345678")).thenReturn(Mono.empty());
            when(rolRepository.findRolById(11L)).thenReturn(Mono.just(new Rol()));
            when(iSecurityUseCase.encodePassword("12345678")).thenReturn(Mono.just("encodedPassword"));
            when(userRepository.saveUser(any(User.class))).thenReturn(Mono.just(user));


            // Act
            Mono<User> result = userUseCase.saveUser(user);

            // Assert
            StepVerifier.create(result)
                    .expectErrorMatches(throwable ->
                            throwable instanceof BusinessException &&
                                    throwable.getMessage().contains("Base salary cannot exceed " + SALARY_BASE_PERMITED)
                    )
                    .verify();

        }

        @Test
        void When_DefaultRoleDoesNotExist_Expect_BusinessException() {
            // Arrange
            User user = UserBuilder.aValidUser().rolId(11L).build();

            when(userRepository.findByEmail("juan.perez@email.com")).thenReturn(Mono.empty());
            when(userRepository.findByDocument("12345678")).thenReturn(Mono.empty());
            when(rolRepository.findRolById(11L)).thenReturn(Mono.empty());
            when(iSecurityUseCase.encodePassword("12345678")).thenReturn(Mono.just("encodedPassword"));
            when(userRepository.saveUser(any(User.class))).thenReturn(Mono.just(user));

            // Act
            Mono<User> result = userUseCase.saveUser(user);

            // Assert
            StepVerifier.create(result)
                    .expectErrorMatches(throwable ->
                            throwable instanceof BusinessException &&
                                    throwable.getMessage().contains("Role does not exist: "+ user.getRolId())
                    )
                    .verify();

        }

}