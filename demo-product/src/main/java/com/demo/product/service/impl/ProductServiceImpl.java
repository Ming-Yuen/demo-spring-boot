package com.demo.product.service.impl;

import com.demo.product.dao.ProductDao;
import com.demo.product.dto.ProductEnquiryRequest;
import com.demo.product.entity.Product;
import com.demo.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Override
    public List<Product> enquiry(ProductEnquiryRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        return productDao.findByActiveDateLessThen(LocalDate.now(), pageable).toList();
    }

    @Override
    public void importDate() throws InterruptedException {
        ArrayList<Product> productList = new ArrayList<Product>();
        for(int i = 0; i < 10000; i++){
            Product product = new Product();
            product.setRegion("HK");
            product.setActiveDate(LocalDate.now());
            product.setName("name");
            product.setSku(UUID.randomUUID().toString());
            productList.add(product);
        }
        log.debug("total " + productList.size());
        productDao.saveAll(productList);
    }
}
