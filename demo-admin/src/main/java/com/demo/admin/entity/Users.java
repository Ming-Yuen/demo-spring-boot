package com.demo.admin.entity;

import com.demo.common.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class Users extends BaseEntity {
    private String userName;
    private String firstName;
    private String lastName;
    private String userPassword;

    private String gender;
    private String email;
    private String phone;
    private Date resignDate;
    private Integer privilege;
}
