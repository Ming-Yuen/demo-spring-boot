package com.demo.product.entity;

import com.demo.admin.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
public class Product extends BaseEntity {
    private String region;
    private String sku;
    private String name;
    private Date activeDate;
}
