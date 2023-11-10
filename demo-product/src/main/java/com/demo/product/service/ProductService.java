package com.demo.product.service;


import com.demo.product.dto.ProductEnquiryRequest;
import com.demo.product.entity.Product;
import com.demo.product.entity.ProductPrice;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {
    List<Product> enquiry(ProductEnquiryRequest request);
    void save(List<Product> products);

    Product existsByProductId(String productId);

    ProductPrice getLatestProductPrice(LocalDate txDate, String productId, String region);
}
