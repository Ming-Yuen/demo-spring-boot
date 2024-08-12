package com.demo.admin.mapping;

import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.Users;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapping {
    List<Users> userRegisterRequestToUser(List<UserRegisterRequest> dto);
}
