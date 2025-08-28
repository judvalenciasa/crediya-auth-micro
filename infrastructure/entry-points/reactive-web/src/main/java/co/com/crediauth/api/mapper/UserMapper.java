package co.com.crediauth.api.mapper;

import co.com.crediauth.api.requestdto.UserRequestDto;
import co.com.crediauth.api.responsedto.UserResponseDto;
import co.com.crediauth.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDto dto);
    UserResponseDto toDto(User user);
}
