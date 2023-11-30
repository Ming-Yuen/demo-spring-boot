package com.demo.admin;

import com.demo.admin.dto.UserRegisterRequest;
import com.demo.common.entity.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserInfo userRegisterRequestToUser(UserRegisterRequest dto);
}
