package co.com.crediauth.api.mapper;

import co.com.crediauth.api.requestdto.rol.RolCreateRequestDto;
import co.com.crediauth.api.requestdto.rol.RolDeleteRequestDto;
import co.com.crediauth.api.requestdto.rol.RolUpdateRequestDto;
import co.com.crediauth.api.responsedto.rol.RolCreateResponseDto;
import co.com.crediauth.model.rol.Rol;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolMapper {
    Rol toEntity(RolCreateRequestDto dto);
    Rol toEntity(RolUpdateRequestDto dto);

    RolCreateResponseDto toDto(Rol rol);

}
