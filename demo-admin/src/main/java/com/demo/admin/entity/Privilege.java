package com.demo.admin.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Privilege extends BaseEntity {
    private Integer privilege;
    private Integer subPrivileges;
}
