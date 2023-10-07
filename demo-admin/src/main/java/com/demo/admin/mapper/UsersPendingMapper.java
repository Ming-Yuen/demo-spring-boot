package com.demo.admin.mapper;

import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.UserInfo;
import com.demo.admin.entity.UserPending;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsersPendingMapper {
    UsersPendingMapper INSTANCE = Mappers.getMapper(UsersPendingMapper.class);
    UserPending userRegisterRequestToUser(UserRegisterRequest userDto);
    UserInfo userPendingConvertUserInfo(UserPending userDto);
}
