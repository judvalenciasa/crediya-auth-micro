package co.com.crediauth.api.mapper;

import co.com.crediauth.api.requestdto.security.UserLoginRequestDto;
import co.com.crediauth.api.responsedto.security.UserLoginResponseDto;
import co.com.crediauth.model.seguridad.LoginRequest;
import co.com.crediauth.model.seguridad.LoginResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    LoginRequest toEntity(UserLoginRequestDto userLoginRequestDto);

}
