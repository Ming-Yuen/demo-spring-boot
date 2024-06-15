package com.demo.admin.entity;

import com.demo.common.entity.BaseEntity;
import com.demo.admin.enums.PrivilegeType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Privilege extends BaseEntity {
    @Column(nullable = false)
    private String privilege;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private PrivilegeType subPrivileges;
    public record SelectSubPrivilege(PrivilegeType subPrivileges){}
}
