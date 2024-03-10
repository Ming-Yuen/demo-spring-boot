package com.demo.product.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class ProductUpdateRequest {
    @NotBlank
    private String org;
    @NotBlank
    private String region;
    @NotBlank
    private String productId;
    @NotBlank
    private String name;
    private ProductPriceRequest[] productPrice;
}
