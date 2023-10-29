package com.demo.common.entity;

import com.demo.common.entity.enums.RoleLevelEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(uniqueConstraints  = {
                @UniqueConstraint(name = "uk_username", columnNames = {"username","updated_at"})
        }
)
public class UserInfo extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private String userName;
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String pwd;
    private String gender;
    private String email;
    private String phone;
    private Date resignDate;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private RoleLevelEnum roleLevel;
}
