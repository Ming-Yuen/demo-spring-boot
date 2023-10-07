package com.demo.admin.entity.enums;

public enum RoleLevelEnum {

    admin(900),
    user(100);

    int level;
    RoleLevelEnum(int level){
        this.level = level;
    }
}
