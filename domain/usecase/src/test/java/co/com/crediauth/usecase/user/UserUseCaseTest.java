package co.com.crediauth.usecase.user;



import co.com.crediauth.model.rol.gateways.RolRepository;
import co.com.crediauth.model.user.User;
import co.com.crediauth.model.user.gateways.UserRepository;
import exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private UserUseCase userUseCase;

    @Test
    void When_UserInformationIsCorrect_Expect_UserToBeSavedCorrectly() {
        // Given
        User user = new User();
        user.setNames("juan");
        user.setLastNames("perez");
        user.setEmail("juan@gmail.com");
        user.setBaseSalary(1000000.0);
        user.setBirthDate(LocalDate.parse("1995-05-09"));
        user.setAddress("Calle 65 H 39-57");
        user.setPhone("3214567890");
        user.setDocumentId("125458522");

        // When
        when(userRepository.existsByEmail("juan@gmail.com")).thenReturn(Mono.just(false));
        when(rolRepository.existsByidRol(11L)).thenReturn(Mono.just(true));
        when(userRepository.saveUser(any(User.class))).thenReturn(Mono.just(user));

        // Then
        Mono<User> result = userUseCase.saveUser(user);

        StepVerifier.create(result)
                .expectNextMatches(savedUser ->
                        savedUser.getEmail().equals("juan@gmail.com") &&
                                savedUser.getNames().equals("juan") &&
                                savedUser.getLastNames().equals("perez") &&
                                savedUser.getBaseSalary() == 1000000.0 &&
                                savedUser.getBirthDate().equals(LocalDate.parse("1995-05-09")) &&
                                savedUser.getAddress().equals("Calle 65 H 39-57") &&
                                savedUser.getPhone().equals("3214567890") &&
                                savedUser.getDocumentId().equals("125458522")
                )
                .verifyComplete();

        // Verify
        verify(userRepository).existsByEmail("juan@gmail.com");
        verify(rolRepository).existsByidRol(11L);
        verify(userRepository).saveUser(user);
    }

    @Test
    void When_EmailAlreadyExists_Expect_BusinessException() {
        // Given
        User user = new User();
        user.setNames("juan");
        user.setLastNames("perez");
        user.setEmail("juan@gmail.com");
        user.setBaseSalary(1000000.0);
        user.setBirthDate(LocalDate.parse("1995-05-09"));
        user.setAddress("Calle 65 H 39-57");
        user.setPhone("3214567890");
        user.setDocumentId("125458522");

        // When
        when(userRepository.existsByEmail("juan@gmail.com")).thenReturn(Mono.just(true));

        // Then
        Mono<User> result = userUseCase.saveUser(user);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof BusinessException &&
                                throwable.getMessage().contains("Email already exists")
                )
                .verify();

        // Verify
        verify(userRepository).existsByEmail("juan@gmail.com");
        verify(rolRepository, never()).existsByidRol(any());
        verify(userRepository, never()).saveUser(any());
    }

    @Test
    void When_BaseSalaryGreaterThan_Expect_BusinessException() {
        // Given
        User user = new User();
        user.setNames("juan");
        user.setLastNames("perez");
        user.setEmail("juan@gmail.com");
        user.setBaseSalary(100000000.0);
        user.setBirthDate(LocalDate.parse("1995-05-09"));
        user.setAddress("Calle 65 H 39-57");
        user.setPhone("3214567890");
        user.setDocumentId("125458522");

        // When
        when(userRepository.existsByEmail("juan@gmail.com")).thenReturn(Mono.just(false));

        // Then
        Mono<User> result = userUseCase.saveUser(user);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof BusinessException &&
                                throwable.getMessage().contains("Base salary cannot exceed 15000000")
                )
                .verify();

        // Verify
        verify(userRepository).existsByEmail("juan@gmail.com");
        verify(rolRepository, never()).existsByidRol(any());
        verify(userRepository, never()).saveUser(any());
    }

    @Test
    void When_DefaultRoleDoesNotExist_Expect_BusinessException() {
        // Given
        User user = new User();
        user.setNames("juan");
        user.setLastNames("perez");
        user.setEmail("juan@gmail.com");
        user.setBaseSalary(1000000.0);
        user.setBirthDate(LocalDate.parse("1995-05-09"));
        user.setAddress("Calle 65 H 39-57");
        user.setPhone("3214567890");
        user.setDocumentId("125458522");

        // When
        when(userRepository.existsByEmail("juan@gmail.com")).thenReturn(Mono.just(false));
        when(rolRepository.existsByidRol(11L)).thenReturn(Mono.just(false));

        // Then
        Mono<User> result = userUseCase.saveUser(user);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof BusinessException &&
                                throwable.getMessage().contains("Default role does not exist")
                )
                .verify();

        // Verify
        verify(userRepository).existsByEmail("juan@gmail.com");
        verify(rolRepository).existsByidRol(11L);
        verify(userRepository, never()).saveUser(any());
    }

    @Test
    void When_AllValidationsPass_Expect_UserToBeSaved() {
        // Given
        User user = new User();
        user.setNames("maria");
        user.setLastNames("garcia");
        user.setEmail("maria@gmail.com");
        user.setBaseSalary(5000000.0);
        user.setBirthDate(LocalDate.parse("1990-03-15"));
        user.setAddress("Calle 123 #45-67");
        user.setPhone("3001234567");
        user.setDocumentId("987654321");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setNames("maria");
        savedUser.setLastNames("garcia");
        savedUser.setEmail("maria@gmail.com");
        savedUser.setBaseSalary(5000000.0);
        savedUser.setBirthDate(LocalDate.parse("1990-03-15"));
        savedUser.setAddress("Calle 123 #45-67");
        savedUser.setPhone("3001234567");
        savedUser.setDocumentId("987654321");

        // When
        when(userRepository.existsByEmail("maria@gmail.com")).thenReturn(Mono.just(false));
        when(rolRepository.existsByidRol(11L)).thenReturn(Mono.just(true));
        when(userRepository.saveUser(any(User.class))).thenReturn(Mono.just(savedUser));

        // Then
        Mono<User> result = userUseCase.saveUser(user);

        StepVerifier.create(result)
                .expectNext(savedUser)
                .verifyComplete();

        // Verify
        verify(userRepository).existsByEmail("maria@gmail.com");
        verify(rolRepository).existsByidRol(11L);
        verify(userRepository).saveUser(user);
    }
}