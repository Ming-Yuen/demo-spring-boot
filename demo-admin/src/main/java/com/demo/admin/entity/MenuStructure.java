package com.demo.admin.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "menuStructure")
public class MenuStructure extends BaseEntity {
    private Long parent;
    private String icon;
    private String name;
    private Integer roleId;
}
