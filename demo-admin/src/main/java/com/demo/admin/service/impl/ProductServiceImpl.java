package com.demo.admin.service.impl;

import com.demo.admin.dao.ProductDao;
import com.demo.admin.dto.ProductEnquiryRequest;
import com.demo.admin.entity.Product;
import com.demo.admin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Override
    public List<Product> enquiry(ProductEnquiryRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        return productDao.findByActiveDateBefore(new Date(), pageable).toList();
    }
}
