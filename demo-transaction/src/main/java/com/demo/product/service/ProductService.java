package com.demo.product.service;


import com.demo.product.dto.ProductEnquiryRequest;
import com.demo.product.entity.Product;
import com.demo.product.entity.ProductPrice;
import com.demo.product.vo.ProductUpdateRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {
    List<Product> enquiry(ProductEnquiryRequest request);
    void save(Product... products);

    boolean existsByProductId(String productId);

    ProductPrice getLatestProductPrice(LocalDate txDate, String region, String productId);

    void save(ProductPrice... price);

    void update(ProductUpdateRequest[] request);

    @Transactional
    void update(Product... products);

    @Transactional
    void update(ProductPrice... productPrices);
}
