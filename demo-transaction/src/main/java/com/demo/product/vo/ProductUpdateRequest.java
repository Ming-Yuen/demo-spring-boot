package com.demo.product.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
