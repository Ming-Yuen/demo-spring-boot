package com.demo.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "userRole"
)
public class UserRole extends BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer roleLevel;
}
