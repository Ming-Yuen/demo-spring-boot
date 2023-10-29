package com.demo.admin.entity;

import com.demo.common.entity.enums.RoleLevelEnum;
import com.demo.admin.entity.enums.StatusEnum;
import com.demo.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(uniqueConstraints  = {
                @UniqueConstraint(name = "uk_userInfoPending", columnNames = {"batch_id", "user_name","updated_at"})
        }
)
public class UserInfoPending extends BaseEntity {
    @Column(name = "batch_id", nullable = false, updatable = false)
    private String batchId;
    @Column(name = "user_name", nullable = false, updatable = false)
    private String userName;
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String pwd;
    private String gender;
    private String email;
    private String phone;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private StatusEnum status;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private RoleLevelEnum roleLevel;
}
