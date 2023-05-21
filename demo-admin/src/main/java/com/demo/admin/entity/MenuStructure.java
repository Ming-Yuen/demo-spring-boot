package com.demo.admin.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "menuStructure")
public class MenuStructure extends BaseEntity {
    private String parent;
    private String menuId;
    private String icon;
    private String name;
    private String role;
}
