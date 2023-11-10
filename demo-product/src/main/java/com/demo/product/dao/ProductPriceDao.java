package com.demo.product.dao;

import com.demo.product.entity.ProductPrice;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface ProductPriceDao extends CrudRepository<ProductPrice, Long> {

    ProductPrice findFirstByEffectiveDateBeforeAndRegionOrderByEffectiveDate(LocalDate txDate, String productId, String region);
}
