package com.demo.admin.entity;

import com.demo.admin.enums.PrivilegeType;
import com.demo.common.entity.BaseEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class MenuStructure extends BaseEntity implements Serializable {
    private String parent;
    private String icon;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String link;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PrivilegeType privilege;
}
