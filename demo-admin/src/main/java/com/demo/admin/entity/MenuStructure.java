package com.demo.admin.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MenuStructure extends BaseEntity {
    private String parent;
    private String icon;
    private String name;
    private String type;
    private String link;
    private Integer privilege;
}
