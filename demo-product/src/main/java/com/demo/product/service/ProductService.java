package com.demo.product.service;


import com.demo.product.dto.ProductEnquiryRequest;
import com.demo.product.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> enquiry(ProductEnquiryRequest request);

//    @Transactional
    void importDate() throws InterruptedException;

    void save(List<Product> products);
}
