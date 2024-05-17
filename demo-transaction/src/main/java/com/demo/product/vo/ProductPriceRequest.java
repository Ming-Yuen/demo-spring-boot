package com.demo.product.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductPriceRequest {
    private String id;
    @NotBlank
    private String productId;
    @NotNull
    private BigDecimal price;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
    private String currencyUnit;
    private String description;
}
