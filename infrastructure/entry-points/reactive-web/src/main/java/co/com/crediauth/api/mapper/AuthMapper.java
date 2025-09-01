package co.com.crediauth.api.mapper;

import co.com.crediauth.api.requestdto.security.UserLoginRequestDto;
import co.com.crediauth.api.responsedto.security.UserLoginResponseDto;
import co.com.crediauth.model.authcredentials.AuthCredentials;
import co.com.crediauth.model.authsession.AuthSession;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    AuthCredentials toEntity(UserLoginRequestDto dto);
    UserLoginResponseDto toDto(AuthSession session);
}
