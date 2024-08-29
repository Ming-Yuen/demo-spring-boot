package com.demo.order.mapper;

import com.demo.common.mapper.CustomMapper;
import com.demo.common.vo.MPFDailyResponse;
import com.demo.order.entity.ProductPrice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface ProductPriceMapping {

    @Mapping(target = "productId", source = "fundId")
    @Mapping(target = "effectiveDate", source = "item.nav.asOfDate")
    @Mapping(target = "price", source = "item.nav.price")
    ProductPrice convert(MPFDailyResponse item);
}
