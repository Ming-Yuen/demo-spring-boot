package org.demo.order.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.math.BigDecimal;
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class SalesOrderItem extends BaseEntity {
    private String orderId;
    private String productId;
    private BigDecimal amount;
    private int qty;
    private BigDecimal unitPrice;
    private String discountMethod;
    private BigDecimal discount;
}
