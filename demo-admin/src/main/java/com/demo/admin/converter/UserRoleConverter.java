package com.demo.admin.converter;

import com.demo.common.entity.enums.UserRole;
import org.mapstruct.factory.Mappers;

public interface UserRoleConverter {
    UserRoleConverter INSTANCE = Mappers.getMapper(UserRoleConverter.class);
    UserRole toUserrole(int userRole);
    int toInt(UserRole userRole);
}
