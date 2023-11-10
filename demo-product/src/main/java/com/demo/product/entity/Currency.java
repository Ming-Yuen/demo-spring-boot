package com.demo.product.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(uniqueConstraints  = {
        @UniqueConstraint(name = "uk_currency", columnNames = {"unit"})
}
)
public class Currency extends BaseEntity {
    private String unit;
    private String rate;
}
