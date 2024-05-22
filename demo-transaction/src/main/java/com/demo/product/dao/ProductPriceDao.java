package com.demo.product.dao;

import com.demo.common.dao.HibernateRepository;
import com.demo.product.entity.ProductPrice;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProductPriceDao extends CrudRepository<ProductPrice, Long>, HibernateRepository<ProductPrice> {

    ProductPrice findFirstByEffectiveDateBeforeAndProductIdAndRegionOrderByEffectiveDate(LocalDate txDate, String productId, String region);

    List<ProductPrice> findProductIdIn(List<String> productIdList);

    <T> List<T> findByProductId(String[] productIds, Class<T> type);
}
