package com.demo.admin.service;

import com.demo.admin.dto.ProductEnquiryRequest;
import com.demo.admin.entity.Product;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductService {
    List<Product> enquiry(ProductEnquiryRequest request);

//    @Transactional
    void importDate() throws InterruptedException;
}
