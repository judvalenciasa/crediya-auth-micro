package co.com.crediauth.r2dbc.mapper;

import co.com.crediauth.model.user.User;
import co.com.crediauth.r2dbc.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {
    public static UserEntity toEntity(User user) {
        if (user == null) return null;

        UserEntity entity = new UserEntity();
        entity.setNames(user.getNames());
        entity.setLastNames(user.getLastNames());
        entity.setEmail(user.getEmail());
        entity.setBaseSalary(user.getBaseSalary());
        entity.setBirthDate(user.getBirthDate());
        entity.setAddress(user.getAddress());
        entity.setPhone(user.getPhone());
        entity.setDocumentId(user.getDocumentId());
        return entity;
    }

    public static User toUser (UserEntity entity) {
        if (entity == null) return null;
        User user = new User();
        user.setId(entity.getId());
        user.setNames(entity.getNames());
        user.setLastNames(entity.getLastNames());
        user.setEmail(entity.getEmail());
        user.setBaseSalary(entity.getBaseSalary());
        user.setBirthDate(entity.getBirthDate());
        user.setAddress(entity.getAddress());
        user.setPhone(entity.getPhone());
        user.setDocumentId(entity.getDocumentId());
        return user;
    }
}