package com.demo.product.converter;

import com.demo.product.vo.manulife.MPFDailyResponse;
import com.demo.product.entity.ProductPrice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ProductPriceConverter {
    @Mapping(target = "region", constant = "HK")
    @Mapping(target = "currencyUnit", constant = "HKD")
    @Mapping(target = "productId", source = "fundId")
    @Mapping(target = "effectiveDate", source = "item.nav.asOfDate")
    @Mapping(target = "price", source = "item.nav.price")
    ProductPrice convert(MPFDailyResponse item);
}
