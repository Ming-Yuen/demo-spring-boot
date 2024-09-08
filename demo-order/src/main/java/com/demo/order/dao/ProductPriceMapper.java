package com.demo.order.dao;

import com.demo.common.mapper.BaseMapper;
import com.demo.order.entity.ProductPrice;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
@Mapper
public interface ProductPriceMapper extends BaseMapper {
    @MapKey("productId")
    Map<String, BigDecimal> findByFixedEffectiveAndProductPrice(@Param("productIds") String[] productIds, @Param("effectiveDate") OffsetDateTime effectiveDate);
    Set<String> findByProductIdAndEffectiveDate(@Param("productPrices") List<ProductPrice> productPrices);
    @MapKey("productId")
    Map<String,BigDecimal> findByProductPrice(@Param("productIds") String[] productIds, @Param("effectiveDates") OffsetDateTime[] effectiveDate);
    void updateProductPrice(@Param("price") ProductPrice productPrice);
}
