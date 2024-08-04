package com.demo.product.service.impl;

import com.demo.common.util.LambdaUtil;
import com.demo.product.dao.ProductPriceRepository;
import com.demo.product.dao.ProductRepository;
import com.demo.product.dto.ProductEnquiryRequest;
import com.demo.product.entity.Product;
import com.demo.product.entity.ProductPrice;
import com.demo.product.mapper.ProductMapper;
import com.demo.product.mapper.ProductPriceMapper;
import com.demo.product.service.ProductService;
import com.demo.product.vo.ProductUpdateRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductPriceRepository productPriceRepository;
    @Autowired
    private ProductPriceMapper productPriceMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> enquiry(ProductEnquiryRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());
        return productRepository.findByEnable(1, pageable).toList();
    }

    @Override
    public void save(Product... products) {
        if (ArrayUtils.isNotEmpty(products)) {
            productRepository.saveAll(Arrays.asList(products));
        }
    }

    @Override
    public boolean existsByProductId(String productId) {
        return productRepository.existsByProductId(productId);
    }

    @Override
    public Map<String, ProductPrice.ProductCurrentPrice> getLatestProductPrice(String[] productId, OffsetDateTime[] txDatetime) {
        List<ProductPrice.ProductCurrentPrice> productPriceList = productPriceRepository.findByProductIdInAndEffectiveDateLessThanEqual(ProductPrice.ProductCurrentPrice.class, productId, OffsetDateTime.now());

        Map<String, ProductPrice.ProductCurrentPrice> productCurrentPriceMap = new HashMap<>();
        for(ProductPrice.ProductCurrentPrice productCurrentPrice : productPriceList){
            ProductPrice.ProductCurrentPrice currentPrice = productCurrentPriceMap.get(productCurrentPrice.productId());
            if(currentPrice == null || productCurrentPrice.effectiveDate().compareTo(currentPrice.effectiveDate()) > 0){
                productCurrentPriceMap.put(productCurrentPrice.productId(), productCurrentPrice);
            }
        }
        return productCurrentPriceMap;
    }

    @Override
    public void save(ProductPrice... price) {
        if (ArrayUtils.isNotEmpty(price)) {
            productPriceRepository.saveAll(Arrays.asList(price));
        }
    }

    @Override
    public void update(ProductUpdateRequest[] request) {
        List<ProductUpdateRequest> latestProductList = Arrays.stream(request).filter(LambdaUtil.distinctByKey(ProductUpdateRequest::getModifyDateTime)).collect(Collectors.toList());

        update(productMapper.convert(latestProductList));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void update(Product... products) {
        String[] productIds = Arrays.stream(products).map(Product::getProductId).toArray(String[]::new);
        Set<String> presentProductIds = productRepository.findByProductIdIn(Product.ProductId.class, productIds).stream().map(Product.ProductId::productId).collect(Collectors.toSet());

        List<Product> productList = Arrays.stream(products)
                .filter(product -> !presentProductIds.contains(product.getProductId()))
                .filter(LambdaUtil.distinctByKey(Product::getProductId))
                .collect(Collectors.toList());
        productRepository.saveAll(productList);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void update(ProductPrice... productPrices) {
        List<ProductPrice> presentProductList = Arrays.asList(productPrices).parallelStream()
                .filter(element -> {
                    ProductPrice.ProductCurrentPrice productPrice = productPriceRepository.findByProductIdAndEffectiveDate(ProductPrice.ProductCurrentPrice.class, element.getProductId(),element.getEffectiveDate());
                    if(productPrice == null || productPrice.price().compareTo(element.getPrice()) != 0){
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
        productPriceRepository.saveAll(presentProductList);
    }

    @Override
    public List<ProductPrice> findByProductPrice(String... productId) {
        return productPriceRepository.findByProductIdIn(productId);
    }
}
