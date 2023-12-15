package com.demo.admin.converter;

import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.UserInfo;
import com.demo.common.entity.enums.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserRoleConverter.class)
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

//    @ValueMapping(source = "roleId", target = "role")
    @Mapping(target = "roleId", source = "role")
    UserInfo[] userRegisterRequestToUser(List<UserRegisterRequest> dto);

    default UserRole map(int value) {
        return UserRole.getRoleById(value);
    }
}
