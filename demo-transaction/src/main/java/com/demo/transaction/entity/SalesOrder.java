package com.demo.transaction.entity;

import com.demo.common.entity.BaseEntity;
import com.demo.transaction.entity.enums.PaymentEnum;
import com.demo.transaction.entity.enums.SalesStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class SalesOrder extends BaseEntity {
    @Column(name = "order_id")
    private String orderId;
    private OffsetDateTime txDatetime;
    private String storeCode;
    private String customerName;
    private String salesPerson;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal discount = BigDecimal.ZERO;

    @Enumerated(EnumType.ORDINAL)
    private PaymentEnum paymentMethod;

    @Enumerated(EnumType.ORDINAL)
    private SalesStatusEnum status;
    private String priority;
    private String shippingMethod;
    private String shippingAddress;
    private String orderSource;
    private String orderType;
    private String remark;

    @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalesOrderItem> items = new ArrayList<>();
    public record OrderId(String orderId){}
}
