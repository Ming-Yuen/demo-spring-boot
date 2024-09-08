package com.demo.order.service.impl;

import com.demo.common.annotation.BatchProcess;
import com.demo.common.util.LambdaUtil;
import com.demo.common.dto.ProductEnquiryRequest;
import com.demo.order.entity.Product;
import com.demo.order.entity.ProductPrice;
import com.demo.order.dao.ProductMapper;
import com.demo.order.dao.ProductPriceMapper;
import com.demo.order.mapper.ProductMapping;
import com.demo.order.service.ProductService;
import com.demo.common.vo.ProductUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    private ProductMapping productMapping;
    private ProductPriceMapper productPriceMapper;
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public List<Product> enquiry(ProductEnquiryRequest request) {
        return productMapper.findByEnable(1, new RowBounds(request.getPageNumber(), request.getPageSize()));
    }

    @Override
    @BatchProcess
    public void updateProduct(List<Product> products) {
        if (CollectionUtils.isNotEmpty(products)) {
            try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)) {
                ProductMapper batchMapper = sqlSession.getMapper(ProductMapper.class);
                Set<String> existingProductIds = productMapper.findByProductIdIn(products);
                for (Product product : products) {
                    if (existingProductIds.contains(product.getProductId())) {
                        batchMapper.updateProducts(product);
                        existingProductIds.add(product.getProductId());
                    } else {
                        batchMapper.insert(product);
                    }
                }
            }
        }
    }


    @Override
    public Map<String, BigDecimal> findByProductPriceAndEffectiveDate(String[] productId, OffsetDateTime[] txDatetime) {
        return productPriceMapper.findByProductPrice(productId,txDatetime);
    }

    @Override
    @BatchProcess
    public void updateProductPrice(List<ProductPrice> productPrices) {
        if (CollectionUtils.isNotEmpty(productPrices)) {
            try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)) {
                Set<String> existingProductIds = productPriceMapper.findByProductIdAndEffectiveDate(productPrices);

                ProductPriceMapper batchMapper = sqlSession.getMapper(ProductPriceMapper.class);
                if (existingProductIds.isEmpty()) {
                    productPrices.stream().forEach(productPrice->batchMapper.insert(productPrice));
                }
                for (ProductPrice productPrice : productPrices) {
                    if (existingProductIds.contains(productPrice.getProductId())) {
                        batchMapper.updateProductPrice(productPrice);
                        existingProductIds.add(productPrice.getProductId());
                    } else {
                        batchMapper.insert(productPrice);
                    }
                }
            }
        }
    }

    @Override
    public void productUpdateRequest(List<ProductUpdateRequest> request) {
        List<ProductUpdateRequest> latestProductList = request.stream().filter(LambdaUtil.distinctByKey(ProductUpdateRequest::getModifyDateTime)).collect(Collectors.toList());

        updateProduct(productMapping.convert(latestProductList));
    }
}
