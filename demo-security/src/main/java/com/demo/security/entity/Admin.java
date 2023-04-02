package com.demo.security.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "admin")
public class Admin extends BaseEntity {
    @NotBlank
    @Column(nullable = false)
    private String adminId;
    @NotBlank
    @Column(nullable = false)
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String gender;
}
