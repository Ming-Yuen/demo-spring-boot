package com.demo.transaction.entity;

import com.demo.common.entity.BaseEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Entity;
import java.math.BigDecimal;
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class SalesOrderItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private SalesOrder salesOrderHeader;

    private String orderId;
    private Integer itemSequence;
    private String productId;
    private BigDecimal amount;
    private int qty;
    private BigDecimal unitPrice;
    private String discountMethod;
    private BigDecimal discount;
}
