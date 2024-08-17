package com.demo.product.dao;

import com.demo.product.entity.ProductPrice;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
@Mapper
public interface ProductPriceMapper {
    @MapKey("productId")
    Map<String, BigDecimal> findByFixedEffectiveAndProductPrice(@Param("productIds") String[] productIds, @Param("effectiveDate") OffsetDateTime effectiveDate);
    Set<String> findByProductIdAndEffectiveDate(@Param("productPrices") List<ProductPrice> productPrices);
    @MapKey("productId")
    Map<String,BigDecimal> findByProductPrice(@Param("productIds") String[] productIds, @Param("effectiveDates") OffsetDateTime[] effectiveDate);

    void insert(List<ProductPrice> productPrices);

    void updateProductPrices(@Param("productPrices") List<ProductPrice> productPrices);
}
