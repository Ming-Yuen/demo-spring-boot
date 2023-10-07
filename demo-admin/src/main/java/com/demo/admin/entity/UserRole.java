package com.demo.admin.entity;

import com.demo.admin.entity.enums.RoleLevelEnum;
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
