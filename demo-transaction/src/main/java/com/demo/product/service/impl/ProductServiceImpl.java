package com.demo.product.service.impl;

import com.demo.product.dao.ProductDao;
import com.demo.product.dao.ProductPriceDao;
import com.demo.product.dto.ProductEnquiryRequest;
import com.demo.product.entity.Product;
import com.demo.product.entity.ProductPrice;
import com.demo.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductPriceDao productPriceDao;
    @Override
    public List<Product> enquiry(ProductEnquiryRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        return productDao.findByActiveDateLessThen(LocalDate.now(), pageable).toList();
    }
    @Override
    public void save(Product... products){
        if(ArrayUtils.isNotEmpty(products)) {
            productDao.saveAll(Arrays.asList(products));
        }
    }

    @Override
    public boolean existsByProductId(String productId) {
        return productDao.existsByProductId(productId);
    }
    @Override
    public ProductPrice getLatestProductPrice(LocalDate txDate, String productId, String region){
        return productPriceDao.findFirstByEffectiveDateBeforeAndProductIdAndRegionOrderByEffectiveDate(txDate, productId, region);
    }

    @Override
    public void save(ProductPrice... price) {
        if(ArrayUtils.isNotEmpty(price)) {
            productPriceDao.saveAll(Arrays.asList(price));
        }
    }
}
