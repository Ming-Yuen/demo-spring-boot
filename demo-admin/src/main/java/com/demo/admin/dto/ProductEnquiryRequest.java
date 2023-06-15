package com.demo.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductEnquiryRequest {
    @NotNull
    private Integer pageNumber;
    @NotNull
    private Integer pageSize;
}
