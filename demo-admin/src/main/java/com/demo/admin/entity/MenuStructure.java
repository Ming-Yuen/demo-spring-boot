package com.demo.admin.entity;

import com.demo.common.entity.BaseEntity;
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
    private String curr;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String link;
    @Column(nullable = false)
    private Long privilege;
}
