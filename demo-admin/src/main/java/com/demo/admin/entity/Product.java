package com.demo.admin.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import java.sql.Date;

@Entity
@Data
public class Product extends BaseEntity {
    private String region;
    private String sku;
    private String name;
    private Date activeDate;
}
