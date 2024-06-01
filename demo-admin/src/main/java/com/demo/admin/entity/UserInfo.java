package com.demo.admin.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
}
