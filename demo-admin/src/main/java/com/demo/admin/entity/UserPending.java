package com.demo.admin.entity;

import com.demo.admin.entity.enums.RoleLevelEnum;
import com.demo.admin.entity.enums.StatusEnum;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_info_pending")
public class UserPending extends BaseEntity {
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
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private StatusEnum status;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private RoleLevelEnum roleLevel;
}
