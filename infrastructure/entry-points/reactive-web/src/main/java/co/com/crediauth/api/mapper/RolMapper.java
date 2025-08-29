package co.com.crediauth.api.mapper;

import co.com.crediauth.api.requestdto.RolUpdateRequestDto;
import co.com.crediauth.api.requestdto.documentExistRequestDto;
import co.com.crediauth.api.responsedto.RolResponseDto;
import co.com.crediauth.model.rol.Rol;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolMapper {
    Rol toEntity(RolUpdateRequestDto dto);
    Rol toEntity(documentExistRequestDto.RolUpdateRequestDto dto);
    RolResponseDto toDto(Rol rol);
}
