package com.demo.product.vo;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Data
public class ProductUpdateRequest {
    @NotBlank
    private String productId;
    @NotBlank
    private String name;
    @NotNull
    private OffsetDateTime modifyDateTime;
    private ProductPriceRequest[] productPrice;
}
