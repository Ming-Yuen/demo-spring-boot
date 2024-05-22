package com.demo.product.service.impl;

import com.demo.common.util.LambdaUtil;
import com.demo.product.dao.ProductDao;
import com.demo.product.dao.ProductPriceDao;
import com.demo.product.dto.ProductEnquiryRequest;
import com.demo.product.entity.Product;
import com.demo.product.entity.ProductPrice;
import com.demo.product.mapper.ProductMapper;
import com.demo.product.mapper.ProductPriceMapper;
import com.demo.product.service.ProductService;
import com.demo.product.vo.ProductUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductPriceDao productPriceDao;
    @Autowired
    private ProductPriceMapper productPriceMapper;
    @Value("${jpaQueryParameterSize}")
    private Integer jpaQueryParameterSize;

    @Override
    public List<Product> enquiry(ProductEnquiryRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        return productDao.findByEnable(1, pageable).toList();
    }

    @Override
    public void save(Product... products) {
        if (ArrayUtils.isNotEmpty(products)) {
            productDao.saveAll(Arrays.asList(products));
        }
    }

    @Override
    public boolean existsByProductId(String productId) {
        return productDao.existsByProductId(productId);
    }

    @Override
    public ProductPrice getLatestProductPrice(LocalDate txDate, String productId, String region) {
        return productPriceDao.findFirstByEffectiveDateBeforeAndProductIdAndRegionOrderByEffectiveDate(txDate, productId, region);
    }

    @Override
    public void save(ProductPrice... price) {
        if (ArrayUtils.isNotEmpty(price)) {
            productPriceDao.saveAll(Arrays.asList(price));
        }
    }

    @Override
    public void update(ProductUpdateRequest[] request) {
        List<ProductUpdateRequest> latestProductList = Arrays.stream(request).filter(LambdaUtil.distinctByKey(ProductUpdateRequest::getModifyDateTime)).collect(Collectors.toList());

        for (int indexProductRequest = 0; indexProductRequest < request.length; indexProductRequest += jpaQueryParameterSize) {
            List<ProductUpdateRequest> productUpdateRequestSubList = latestProductList.subList(indexProductRequest, Math.min(indexProductRequest + jpaQueryParameterSize, latestProductList.size()));

            update(productMapper.convert(productUpdateRequestSubList));
        }
    }

    @Transactional
    @Override
    public void update(Product... products) {
        String[] productIds = Arrays.stream(products).map(Product::getProductId).toArray(String[]::new);
        Set<String> presentProductIds = productDao.findByProductId(productIds, Product.ProductId.class).stream().map(Product.ProductId::getProductId).collect(Collectors.toSet());

        productDao.persistAll(Arrays.stream(products).filter(product -> !presentProductIds.contains(product.getProductId())).collect(Collectors.toList()));
    }
    @Transactional
    @Override
    public void update(ProductPrice... productPrices) {
        String[] productIds = Arrays.stream(productPrices).map(ProductPrice::getProductId).toArray(String[]::new);
        productPriceDao.findByProductId(productIds, ProductPrice.ProductPriceId.class).stream().map(ProductPrice.ProductPriceId::getProductId).collect(Collectors.toSet());
    }
}
