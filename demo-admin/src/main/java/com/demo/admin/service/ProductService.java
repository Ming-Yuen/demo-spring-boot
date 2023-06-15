package com.demo.admin.service;

import com.demo.admin.dto.ProductEnquiryRequest;
import com.demo.admin.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> enquiry(ProductEnquiryRequest request);
}
