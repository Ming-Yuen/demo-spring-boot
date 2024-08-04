package com.demo.product.dao;

import com.demo.product.entity.ProductPrice;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
=======
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
>>>>>>> d414bcab456dc1ad320d34b2c37933f206063ba1

import java.time.OffsetDateTime;
import java.util.List;

<<<<<<< HEAD
public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long>, JpaSpecificationExecutor<ProductPrice>{
=======
public interface ProductPriceRepository extends CrudRepository<ProductPrice, Long>, JpaSpecificationExecutor<ProductPrice>{
>>>>>>> d414bcab456dc1ad320d34b2c37933f206063ba1

    <T> List<T> findByProductIdInAndEffectiveDateLessThanEqual(Class<T> type, String[] productId, OffsetDateTime current);
    List<ProductPrice> findByProductIdIn(String... productIds);

    <T> List<T> findByProductIdIn(Class<T> type, String... productIds);
    <T> List<T> findByProductIdInAndEffectiveDateIn(Class<T> type, String[] productIds, OffsetDateTime[] effectiveDate);
    List<ProductPrice> findByProductIdInAndEffectiveDateIn(String[] productIds, OffsetDateTime[] effectiveDate);

    List<ProductPrice> findByProductIdInAndVersionIn(String[] productIds, Integer[] effectiveDate);
    <T> T findByProductIdAndEffectiveDate(Class<T> type, String productIds, OffsetDateTime effectiveDate);

}
