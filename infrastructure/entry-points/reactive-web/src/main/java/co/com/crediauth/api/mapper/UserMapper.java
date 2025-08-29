package co.com.crediauth.api.mapper;

import co.com.crediauth.api.requestdto.user.UserCreateRequestDto;
import co.com.crediauth.api.responsedto.user.UserResponseDto;
import co.com.crediauth.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserCreateRequestDto dto);
    UserResponseDto toDto(User user);
}
