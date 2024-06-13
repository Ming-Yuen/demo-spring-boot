package com.demo.admin.entity;

import com.demo.common.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class UserInfo extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private String userName;
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String userPassword;

    private String gender;
    private String email;
    private String phone;
    private Date resignDate;

    @Column(nullable = false)
    private String role;
    public record SelectUserName(Long id, String userName){}
    public record SelectUserPwd(String userName, String userPwd){}
    public record SelectUserRole(String role){}
}
