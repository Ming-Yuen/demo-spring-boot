package com.demo.admin.entity;

import com.demo.common.entity.enums.RoleLevelEnum;
import com.demo.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "userRole"
)
public class UserRole extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private RoleLevelEnum roleLevel;
}
