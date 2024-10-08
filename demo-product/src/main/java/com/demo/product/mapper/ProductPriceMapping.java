package com.demo.product.mapper;

import com.demo.common.mapper.CustomMapper;
import com.demo.product.entity.ProductPrice;
import com.demo.common.vo.MPFDailyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface ProductPriceMapping {

    @Mapping(target = "productId", source = "fundId")
    @Mapping(target = "effectiveDate", source = "item.nav.asOfDate")
    @Mapping(target = "price", source = "item.nav.price")
    ProductPrice convert(MPFDailyResponse item);
}
