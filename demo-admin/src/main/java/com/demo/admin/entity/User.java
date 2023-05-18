package com.demo.admin.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "users",
        indexes = {
            @Index( name = "idx_username", columnList = "username")
        }
)
public class User extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private String username;
    @Column(nullable = true)
    private String firstName;
    @Column(nullable = true)
    private String lastName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = true)
    private String email;
    @Column(nullable = true)
    private String phone;
    @Column(nullable = false)
    private String role;
}
