package com.demo.admin.entity;

import com.demo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "userRole",
        uniqueConstraints  = {
                @UniqueConstraint(name = "uk_userRole", columnNames = "name")
        }
)
public class UserRole extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private com.demo.common.entity.enums.UserRole roleLevel;
}
