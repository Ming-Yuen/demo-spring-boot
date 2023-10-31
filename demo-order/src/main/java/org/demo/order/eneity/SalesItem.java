package org.demo.order.eneity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.math.BigDecimal;
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class SalesItem extends BaseEntity {
    private String sku;
    private BigDecimal amount;
    private int qty;
}
