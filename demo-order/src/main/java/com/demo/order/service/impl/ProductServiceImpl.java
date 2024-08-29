package com.demo.order.service.impl;

import com.demo.common.util.LambdaUtil;
import com.demo.common.dto.ProductEnquiryRequest;
import com.demo.order.entity.Product;
import com.demo.order.entity.ProductPrice;
import com.demo.order.dao.ProductMapper;
import com.demo.order.dao.ProductPriceMapper;
import com.demo.order.mapper.ProductMapping;
import com.demo.order.mapper.ProductPriceMapping;
import com.demo.order.service.ProductService;
import com.demo.common.vo.ProductUpdateRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductMapping productMapping;
    @Autowired
    private ProductPriceMapper productPriceMapper;
    @Autowired
    private ProductPriceMapping productPriceMapping;

    @Override
    public PageInfo<Product> enquiry(ProductEnquiryRequest request) {
        PageHelper.startPage(request.getPageNumber(), request.getPageSize());
        return new PageInfo<>(productMapper.findByEnable(1));
    }

    @Override
    public void updateProduct(List<Product> products) {
        if (CollectionUtils.isNotEmpty(products)) {
            Set<String> existingProductIds = productMapper.findByProductIdIn(products);
            ArrayList<Product> productsForInsert = new ArrayList<>();
            ArrayList<Product> productsForUpdate = new ArrayList<>();
            for(Product product : products) {
                if(existingProductIds.contains(product.getProductId())){
                    productsForUpdate.add(product);
                }else{
                    productsForInsert.add(product);
                    existingProductIds.add(product.getProductId());
                }
            }
            if(!productsForInsert.isEmpty()) {
                productMapper.insert(productsForInsert);
            }
            if(!productsForUpdate.isEmpty()) {
                productMapper.updateProducts(productsForUpdate);
            }
        }
    }

    @Override
    public Map<String, BigDecimal> findByProductPriceAndEffectiveDate(String[] productId, OffsetDateTime[] txDatetime) {
        return productPriceMapper.findByProductPrice(productId,txDatetime);
    }

    @Override
    public void updateProductPrice(List<ProductPrice> productPrices) {
        if (CollectionUtils.isNotEmpty(productPrices)) {
            Set<String> existingProductIds = productPriceMapper.findByProductIdAndEffectiveDate(productPrices);
            if(existingProductIds.isEmpty()){
                productPriceMapper.insert(productPrices);
            }
            ArrayList<ProductPrice> productsForInsert = new ArrayList<>();
            ArrayList<ProductPrice> productsForUpdate = new ArrayList<>();
            for(ProductPrice productPrice : productPrices) {
                if(existingProductIds.contains(productPrice.getProductId())){
                    productsForUpdate.add(productPrice);
                }else{
                    productsForInsert.add(productPrice);
                    existingProductIds.add(productPrice.getProductId());
                }
            }
            if(!productsForInsert.isEmpty()) {
                productPriceMapper.insert(productsForInsert);
            }
            if(!productsForUpdate.isEmpty()) {
                productPriceMapper.updateProductPrices(productsForUpdate);
            }
        }
    }

    @Override
    public void productUpdateRequest(List<ProductUpdateRequest> request) {
        List<ProductUpdateRequest> latestProductList = request.stream().filter(LambdaUtil.distinctByKey(ProductUpdateRequest::getModifyDateTime)).collect(Collectors.toList());

        updateProduct(productMapping.convert(latestProductList));
    }
}
