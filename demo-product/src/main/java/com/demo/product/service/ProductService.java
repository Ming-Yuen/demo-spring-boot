package com.demo.product.service;


import com.demo.common.dto.ProductEnquiryRequest;
import com.demo.product.entity.Product;
import com.demo.product.entity.ProductPrice;
import com.demo.common.vo.ProductUpdateRequest;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public interface ProductService {
    PageInfo<Product> enquiry(ProductEnquiryRequest request);
    void updateProduct(List<Product> products);

    Map<String, BigDecimal> findByProductPriceAndEffectiveDate(String[] productId, OffsetDateTime[] txDatetime);

    void updateProductPrice(List<ProductPrice> price);

    void productUpdateRequest(List<ProductUpdateRequest> request);
}
