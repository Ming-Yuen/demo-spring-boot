package com.demo.order.service;


import com.demo.common.dto.ProductEnquiryRequest;
import com.demo.common.vo.ProductUpdateRequest;
import com.demo.order.entity.Product;
import com.demo.order.entity.ProductPrice;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> enquiry(ProductEnquiryRequest request);
    void updateProduct(List<Product> products);

    Map<String, BigDecimal> findByProductPriceAndEffectiveDate(String[] productId, OffsetDateTime[] txDatetime);

    void updateProductPrice(List<ProductPrice> price);

    void productUpdateRequest(List<ProductUpdateRequest> request);
}
