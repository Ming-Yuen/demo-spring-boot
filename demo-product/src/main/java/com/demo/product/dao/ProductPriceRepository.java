package com.demo.product.dao;

import com.demo.product.entity.ProductPrice;
import org.springframework.data.repository.CrudRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface ProductPriceRepository extends CrudRepository<ProductPrice, Long>{

    ProductPrice findFirstByEffectiveDateBeforeAndProductIdOrderByEffectiveDate(OffsetDateTime txDatetime, String productId);

    <T> List<T> findByProductId(String[] productIds, Class<T> type);
}
