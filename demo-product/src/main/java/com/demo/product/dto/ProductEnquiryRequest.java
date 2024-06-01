package com.demo.product.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class ProductEnquiryRequest {
    @NotNull
    private Integer pageNumber;
    @NotNull
    private Integer pageSize;
}
