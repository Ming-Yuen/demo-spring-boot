package com.demo.common.entity.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    admin("900"),
    user("100");

    final String level;
    UserRole(String level){
        this.level = level;
    }

    public static UserRole getRoleById(String value) {
        for(UserRole userRole : UserRole.values()){
            if(userRole.level.equals(value)){
                return userRole;
            }
        }
        throw new IllegalArgumentException("Invalid User role value: " + value);
    }
}
