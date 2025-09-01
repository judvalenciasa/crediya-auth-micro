package rol;


import co.com.crediauth.model.rol.Rol;
import co.com.crediauth.model.rol.gateways.RolRepository;
import co.com.crediauth.usecase.rol.RolUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RolUseCaseTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolUseCase  rolUseCase;

    @Test
    void When_CreateRolWithValidData_Expect_RolToBeCreated() {
        // Arrange
        Rol rol = Rol.builder()
                .idRol(1L)
                .name("ADMIN")
                .description("Administrator role")
                .build();

        Rol savedRol = Rol.builder()
                .idRol(1L)
                .name("ADMIN")
                .description("Administrator role")
                .build();

        when(rolRepository.saveRol(any(Rol.class))).thenReturn(Mono.just(savedRol));

        // Act
        Mono<Rol> result = rolUseCase.createRol(rol);

        // Assert
        StepVerifier.create(result)
                .expectNext(savedRol)
                .verifyComplete();

        verify(rolRepository).saveRol(rol);
    }

    @Test
    void When_CreateRolWithRepositoryError_Expect_ErrorToBePropagated() {
        // Arrange
        Rol rol = Rol.builder()
                .idRol(1L)
                .name("ADMIN")
                .description("Administrator role")
                .build();

        RuntimeException expectedError = new RuntimeException("Database error");
        when(rolRepository.saveRol(any(Rol.class))).thenReturn(Mono.error(expectedError));

        // Act
        Mono<Rol> result = rolUseCase.createRol(rol);

        // Assert
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        verify(rolRepository).saveRol(rol);
    }

    @Test
    void When_UpdateRolWithValidId_Expect_RolToBeFound() {
        // Arrange
        Long rolId = 1L;
        Rol rol = Rol.builder()
                .idRol(rolId)
                .name("ADMIN")
                .description("Administrator role")
                .build();

        when(rolRepository.findRolById(rolId)).thenReturn(Mono.just(rol));

        // Act
        Mono<Rol> result = rolUseCase.updateRol(rol);

        // Assert
        StepVerifier.create(result)
                .expectNext(rol)
                .verifyComplete();

        verify(rolRepository).findRolById(rolId);
    }

    @Test
    void When_UpdateRolWithInvalidId_Expect_EmptyResult() {
        // Arrange
        Long rolId = 999L;
        Rol rol = Rol.builder()
                .idRol(rolId)
                .name("INVALID")
                .description("Invalid role")
                .build();

        when(rolRepository.findRolById(rolId)).thenReturn(Mono.empty());

        // Act
        Mono<Rol> result = rolUseCase.updateRol(rol);

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(rolRepository).findRolById(rolId);
    }

    @Test
    void When_UpdateRolWithRepositoryError_Expect_ErrorToBePropagated() {
        // Arrange
        Long rolId = 1L;
        Rol rol = Rol.builder()
                .idRol(rolId)
                .name("ADMIN")
                .description("Administrator role")
                .build();

        RuntimeException expectedError = new RuntimeException("Database error");
        when(rolRepository.findRolById(rolId)).thenReturn(Mono.error(expectedError));

        // Act
        Mono<Rol> result = rolUseCase.updateRol(rol);

        // Assert
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        verify(rolRepository).findRolById(rolId);
    }

    @Test
    void When_DeleteRolWithValidId_Expect_TrueToBeReturned() {
        // Arrange
        Long rolId = 1L;
        when(rolRepository.deleteRol(rolId)).thenReturn(Mono.just(true));

        // Act
        Mono<Boolean> result = rolUseCase.deleteRol(rolId);

        // Assert
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(rolRepository).deleteRol(rolId);
    }

    @Test
    void When_DeleteRolWithInvalidId_Expect_FalseToBeReturned() {
        // Arrange
        Long rolId = 999L;
        when(rolRepository.deleteRol(rolId)).thenReturn(Mono.just(false));

        // Act
        Mono<Boolean> result = rolUseCase.deleteRol(rolId);

        // Assert
        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();

        verify(rolRepository).deleteRol(rolId);
    }

    @Test
    void When_DeleteRolWithRepositoryError_Expect_ErrorToBePropagated() {
        // Arrange
        Long rolId = 1L;
        RuntimeException expectedError = new RuntimeException("Database error");
        when(rolRepository.deleteRol(rolId)).thenReturn(Mono.error(expectedError));

        // Act
        Mono<Boolean> result = rolUseCase.deleteRol(rolId);

        // Assert
        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        verify(rolRepository).deleteRol(rolId);
    }

}
