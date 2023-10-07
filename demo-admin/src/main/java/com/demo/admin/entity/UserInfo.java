package com.demo.admin.entity;

import com.demo.admin.entity.enums.RoleLevelEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "userInfo")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserInfo extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private String userName;
    @Column(nullable = true)
    private String firstName;
    @Column(nullable = true)
    private String lastName;
    @Column(nullable = false)
    private String pwd;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = true)
    private String email;
    @Column(nullable = true)
    private String phone;
    private Date resignDate;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private RoleLevelEnum roleLevel;
}
