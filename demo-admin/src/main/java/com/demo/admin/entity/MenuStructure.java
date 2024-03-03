package com.demo.admin.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "menuStructure",
        uniqueConstraints  = {
                @UniqueConstraint(name = "uk_menuStructure", columnNames = "name")
        }
)
public class MenuStructure extends BaseEntity {
    private Long parent;
    private String icon;
    private String name;
    private String type;
    private String link;
    private Long roleId;
}
