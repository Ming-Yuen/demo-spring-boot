package com.demo.product.service;


import com.demo.product.dto.ProductEnquiryRequest;
import com.demo.product.entity.Product;
import com.demo.product.entity.ProductPrice;
import com.demo.product.vo.ProductUpdateRequest;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> enquiry(ProductEnquiryRequest request);
    void save(Product... products);

    boolean existsByProductId(String productId);

    Map<String, ProductPrice.ProductCurrentPrice> getLatestProductPrice(String[] productId, OffsetDateTime[] txDatetime);

    void save(ProductPrice... price);

    void update(ProductUpdateRequest[] request);


    void update(Product... products);

    void update(ProductPrice... productPrices);

    List<ProductPrice> findByProductPrice(String... productId);
}
