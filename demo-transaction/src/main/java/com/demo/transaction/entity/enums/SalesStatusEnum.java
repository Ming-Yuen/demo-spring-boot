package com.demo.transaction.entity.enums;

public enum SalesStatusEnum {
    sales(10), cancel(20);
    private Integer status;
    SalesStatusEnum(Integer status){
        this.status = status;
    }
}
