package com.demo.admin.mapper;

import com.demo.admin.dto.UserRegisterRequest;
import com.demo.common.entity.UserInfo;
import com.demo.admin.entity.UserInfoPending;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsersPendingMapper {
    UsersPendingMapper INSTANCE = Mappers.getMapper(UsersPendingMapper.class);
    UserInfoPending userRegisterRequestToUser(UserRegisterRequest userDto);
    UserInfo convertToUserInfo(UserInfoPending userDto);
}
