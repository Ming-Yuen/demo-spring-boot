package org.demo.order.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.demo.order.entity.enums.PaymentEnum;
import org.demo.order.entity.enums.SalesStatusEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class SalesOrder extends BaseEntity {
    private String orderId;
    private OffsetDateTime txDatetime;
    private String storeCode;
    private String customerName;
    private String salesPerson;
    private BigDecimal totalAmount;
    private BigDecimal discount;

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
}
