package com.demo.admin.enums;

import lombok.Getter;

@Getter
public enum PrivilegeType {

    admin("900"),
    user("100");

    final String privilege;
    PrivilegeType(String privilege){
        this.privilege = privilege;
    }

}
