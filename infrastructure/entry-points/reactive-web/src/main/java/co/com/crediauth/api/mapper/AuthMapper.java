package co.com.crediauth.api.mapper;

import co.com.crediauth.api.requestdto.security.UserLoginRequestDto;
import co.com.crediauth.model.seguridad.LoginRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    LoginRequest toEntity(UserLoginRequestDto userLoginRequestDto);

}
