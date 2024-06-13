package com.demo.admin.mapper;

import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.UserInfo;
import com.demo.common.entity.enums.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserConverter {
//    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);
    @Mapping(target = "pwd", source = "password")
    UserInfo[] userRegisterRequestToUser(UserRegisterRequest[] dto);

    default UserRole map(String value) {
        return UserRole.getRoleById(value);
    }
}
