package com.demo.admin.eneity;

import com.demo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
@Data
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @NotBlank
    @Column(nullable = false)
    private String userName;
    @NotBlank
    @Column(nullable = false)
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String gender;
}
