package com.demo.common.entity;

import com.demo.common.entity.enums.UserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(uniqueConstraints  = {
                @UniqueConstraint(name = "uk_username", columnNames = {"username"})
        }
)
@Cacheable(cacheNames = "userInfo", key = "#userInfo.username")
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
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private UserRole role;
}
