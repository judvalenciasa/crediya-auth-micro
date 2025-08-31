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
        return rolRepository.findRolById(rol.getIdRol());
    }

    @Override
    public Mono<Boolean> deleteRol(Long idRol) {
        return rolRepository.deleteRol(idRol);
    }

    
}
