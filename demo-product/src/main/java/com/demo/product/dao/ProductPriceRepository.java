package com.demo.product.dao;

import com.demo.product.entity.ProductPrice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface ProductPriceRepository extends CrudRepository<ProductPrice, Long>, JpaSpecificationExecutor<ProductPrice>{

    <T> List<T> findByProductIdInAndEffectiveDateLessThanEqual(Class<T> type, String[] productId, OffsetDateTime current);
    List<ProductPrice> findByProductIdIn(String... productIds);

    <T> List<T> findByProductIdIn(Class<T> type, String... productIds);
    <T> List<T> findByProductIdInAndEffectiveDateIn(Class<T> type, String[] productIds, OffsetDateTime[] effectiveDate);
    List<ProductPrice> findByProductIdInAndEffectiveDateIn(String[] productIds, OffsetDateTime[] effectiveDate);

    List<ProductPrice> findByProductIdInAndVersionIn(String[] productIds, Integer[] effectiveDate);
    <T> T findByProductIdAndEffectiveDate(Class<T> type, String productIds, OffsetDateTime effectiveDate);

}
