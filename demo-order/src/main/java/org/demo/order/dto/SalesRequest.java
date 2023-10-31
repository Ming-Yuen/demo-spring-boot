package org.demo.order.dto;

import lombok.Data;
import lombok.experimental.Delegate;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class SalesRequest {
    private String orderId;
    private OffsetDateTime txDate;
    private Integer version;
    private String createdBy;
    private OffsetDateTime createdAt;
//    @Delegate
    private List<SalesItem> salesItems;
    @Data
    public static class SalesItem{
        private String sku;
        private BigDecimal amount;
        private int qty;

    }
}
