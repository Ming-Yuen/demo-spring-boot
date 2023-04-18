package com.demo.admin.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "userRole",
        indexes = {
                @Index( name = "idx_role", columnList = "role")
        }
)
public class UserRole extends BaseEntity {
    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer roleLevel;
}
