package com.demo.common.entity.enums;

public enum UserRole {

    admin(900),
    user(100);

    int level;
    UserRole(int level){
        this.level = level;
    }

    public static UserRole getRoleById(int value) {
        for(UserRole userRole : UserRole.values()){
            if(value == userRole.level){
                return userRole;
            }
        }
        throw new IllegalArgumentException("Invalid User role value: " + value);
    }
}
