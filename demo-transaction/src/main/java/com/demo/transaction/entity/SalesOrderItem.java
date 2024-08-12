package com.demo.transaction.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Setter
@Getter
public class SalesOrderItem extends BaseEntity {

    private String orderId;
    private SalesOrder salesOrder;
    private OffsetDateTime txDatetime;
    private Integer itemSequence;
    private String productId;
    private BigDecimal amount;
    private Integer qty;
    private BigDecimal unitPrice;
    private String discountMethod;
    private BigDecimal discount;
}
