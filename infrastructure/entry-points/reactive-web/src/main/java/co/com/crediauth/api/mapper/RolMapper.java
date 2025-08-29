package co.com.crediauth.api.mapper;

import co.com.crediauth.api.requestdto.RolRequestDto;
import co.com.crediauth.api.requestdto.RolUpdateRequestDto;
import co.com.crediauth.api.responsedto.RolResponseDto;
import co.com.crediauth.model.rol.Rol;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolMapper {
    Rol toEntity(RolRequestDto dto);
    Rol toEntity(RolUpdateRequestDto dto);
    RolResponseDto toDto(Rol rol);
}
