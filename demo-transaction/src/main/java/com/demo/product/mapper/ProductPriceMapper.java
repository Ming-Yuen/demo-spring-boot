package com.demo.product.mapper;

import com.demo.common.mapper.CustomMapper;
import com.demo.product.entity.ProductPrice;
import com.demo.product.vo.ProductPriceRequest;
import com.demo.product.vo.manulife.MPFDailyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", uses= CustomMapper.class)
public interface ProductPriceMapper {
    ProductPriceMapper INSTANCE = Mappers.getMapper(ProductPriceMapper.class);

    @Mapping(target = "currencyUnit", constant = "HKD")
    @Mapping(target = "productId", source = "fundId")
    @Mapping(target = "description", source = "fundName")
    @Mapping(target = "effectiveDate", source = "item.nav.asOfDate", qualifiedByName="convertOffsetDatetime")
    @Mapping(target = "price", source = "item.nav.price")
    ProductPrice convert(MPFDailyResponse item);
}
