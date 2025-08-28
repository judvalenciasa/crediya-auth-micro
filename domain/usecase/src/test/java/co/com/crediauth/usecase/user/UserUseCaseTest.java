package co.com.crediauth.usecase.user;



import co.com.crediauth.model.user.User;
import co.com.crediauth.model.user.gateways.UserRepository;
import exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {


    private UserRepository userRepository;
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userUseCase = new UserUseCase(userRepository);
    }

    @Test
    void When_UserInformationIsCorrect_Expect_UserToBeSavedCorrectly() {
        // 1️⃣ Crear usuario de prueba
        User user = new User();
        user.setNames("juan");
        user.setLastNames("perez");
        user.setEmail("juan@gmail.com");
        user.setBaseSalary(1000000.0);
        user.setBirthDate(LocalDate.parse("1995-05-09"));
        user.setAddress("Calle 65 H 39-57");
        user.setPhone("3214567890");
        user.setDocumentId("125458522");

        // 2️⃣ Mock del repositorio
        when(userRepository.existsByEmail("juan@gmail.com")).thenReturn(Mono.just(false));
        when(userRepository.saveUser(any(User.class))).thenReturn(Mono.just(user));

        // 3️⃣ Ejecutar el UseCase
        Mono<User> result = userUseCase.saveUser(user);

        // 4️⃣ Verificar resultado con StepVerifier
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

        // 5️⃣ Verificar que se llamaron los métodos del mock
        verify(userRepository).existsByEmail("juan@gmail.com");
        verify(userRepository).saveUser(user);
    }


}