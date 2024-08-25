package com.demo.admin.mapper;

import com.demo.common.dto.UserRegisterRequest;
import com.demo.admin.entity.Users;
import com.demo.common.enums.PrivilegeType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users[] userRegisterRequestToUser(UserRegisterRequest[] dto);

    default PrivilegeType map(String value) {
        return PrivilegeType.valueOf(value);
    }
}
