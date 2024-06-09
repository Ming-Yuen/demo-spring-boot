package com.demo.admin.entity;

import com.demo.common.entity.BaseEntity;
import lombok.*;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class UserInfo extends BaseEntity implements Serializable {
    @Column(nullable = false, updatable = false)
    private String userName;
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String userPwd;

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
