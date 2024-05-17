package com.demo.product.service.impl;

import com.demo.product.dao.ProductDao;
import com.demo.product.dao.ProductPriceDao;
import com.demo.product.dto.ProductEnquiryRequest;
import com.demo.product.entity.Product;
import com.demo.product.entity.ProductPrice;
import com.demo.product.entity.QProduct;
import com.demo.product.mapper.ProductMapper;
import com.demo.product.mapper.ProductPriceMapper;
import com.demo.product.service.ProductService;
import com.demo.product.vo.ProductPriceRequest;
import com.demo.product.vo.ProductUpdateRequest;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Autowired
    private EntityManager entityManager;
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

    @Transactional
    @Override
    public void update(ProductUpdateRequest[] request) {
        Map<String, Map<String, List<ProductUpdateRequest>>> groupedData = Arrays.stream(request).collect(Collectors.groupingBy(ProductUpdateRequest::getOrg, Collectors.groupingBy(ProductUpdateRequest::getRegion)));

        QProduct qProduct = QProduct.product;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        for(String org : groupedData.keySet()){
            Map<String, List<ProductUpdateRequest>> regionData = groupedData.get(org);
            for(String region : regionData.keySet()){
                List<ProductUpdateRequest> productUpdateRequestList = regionData.get(region);

                for(int index = 0; index < productUpdateRequestList.size(); index+=jpaQueryParameterSize){
                    List<ProductUpdateRequest> productUpdateRequestStream = productUpdateRequestList.subList(index, Math.min(index + jpaQueryParameterSize, productUpdateRequestList.size()));

                    String[] productIds = productUpdateRequestStream.stream().map(ProductUpdateRequest::getProductId).toArray(String[]::new);
                    HashSet<String> presentProductIds = new HashSet<>(queryFactory.select(qProduct.productId).where(qProduct.org.eq(org).and(qProduct.region.eq(region)).and(qProduct.productId.in(productIds))).fetch());
                    List<Product> absentProducts = productMapper.convert(productUpdateRequestList.stream().filter(product->!presentProductIds.contains(product.getProductId())).collect(Collectors.toList()));
                    productDao.persistAll(absentProducts);

                    List<ProductPrice> presentProductPriceList = new ArrayList<>();
                    List<ProductPrice> absentProductPriceList = new ArrayList<>();
                    for(ProductUpdateRequest productUpdateRequest : productUpdateRequestList){
                        if(productUpdateRequest.getProductPrice() != null){
                            for(ProductPriceRequest productPrice : productUpdateRequest.getProductPrice()){
                                if(productPrice.getId() != null){
                                    presentProductPriceList.add(productPriceMapper.convert(productPrice));
                                }else{
                                    absentProductPriceList.add(productPriceMapper.convert(productPrice));
                                }
                            }
                        }
                    }
                    productPriceDao.peristAllAndFlush(absentProductPriceList);
                    productPriceDao.mergeAllAndFlush(presentProductPriceList);
                }
            }
        }
    }
}
