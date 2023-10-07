package com.demo.admin.entity.enums;

public enum StatusEnum {
    PENDING(0),
    ERROR(-1),
    SUCCESS(1);
    int status;
    StatusEnum(int status){
       this.status = status;
    }
}
