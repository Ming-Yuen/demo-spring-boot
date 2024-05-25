package com.demo.admin.entity;

import com.demo.admin.enums.Gender;
import com.demo.common.entity.BaseEntity;
import com.demo.common.entity.enums.UserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "users",
        uniqueConstraints  = {
                @UniqueConstraint(name = "uk_username", columnNames = {"user_name"})
        }
)
public class User extends BaseEntity implements Serializable {
    @Column(nullable = false, updatable = false)
    private String userName;
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String password;

    private String gender;
    private String email;
    private String phone;
    private Date resignDate;

    @Column(nullable = false)
    private String role;
}
