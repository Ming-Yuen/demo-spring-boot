package com.demo.admin.converter;

import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.User;
import com.demo.common.entity.enums.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    User[] userRegisterRequestToUser(List<UserRegisterRequest> dto);

    default UserRole map(int value) {
        return UserRole.getRoleById(value);
    }
}
