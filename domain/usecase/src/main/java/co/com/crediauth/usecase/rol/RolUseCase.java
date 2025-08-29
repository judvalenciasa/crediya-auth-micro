package co.com.crediauth.usecase.rol;

import co.com.crediauth.model.rol.Rol;
import co.com.crediauth.model.rol.gateways.RolRepository;
import exception.AdminException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class RolUseCase implements IRolUseCase{
    private final RolRepository rolRepository;

    @Override
    public Mono<Rol> createRol(Rol rol) {
        return rolRepository.saveRol(rol);
    }

    @Override
    public Mono<Rol> updateRol(Rol rol) {
        return rolRepository.existsByidRol(rol.getIdRol())
                .flatMap(exists -> {
                    if (exists) {
                        return rolRepository.saveRol(rol);
                    } else {
                        return Mono.error(new AdminException("Rol no encontrado con ID: " + rol.getIdRol()));
                    }
                });
    }

    @Override
    public Mono<Void> deleteRol(Long idRol) {
        return rolRepository.existsByidRol(idRol)
                .flatMap(exists -> {
                    if (exists) {
                        return rolRepository.deleteRol(idRol);
                    } else {
                        return Mono.error(new AdminException("Rol no encontrado con ID: " + idRol));
                    }
                });
    }
}
