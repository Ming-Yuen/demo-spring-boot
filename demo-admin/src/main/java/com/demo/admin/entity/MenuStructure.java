package com.demo.admin.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "menu_structure",
        uniqueConstraints  = {
                @UniqueConstraint(name = "uk_menuStructure", columnNames = "title")
        }
)
public class MenuStructure extends BaseEntity implements Serializable {
    private String parent;
    private String icon;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String link;
    @Column(nullable = false)
    private Long roleId;
}
