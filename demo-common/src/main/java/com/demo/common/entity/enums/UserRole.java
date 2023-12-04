package com.demo.common.entity.enums;

public enum UserRole {

    admin(900),
    user(100);

    int level;
    UserRole(int level){
        this.level = level;
    }
}
