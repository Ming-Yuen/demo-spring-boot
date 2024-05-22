package com.demo.product.mapper;

import com.demo.product.entity.ProductPrice;
import com.demo.product.vo.ProductPriceRequest;
import com.demo.product.vo.manulife.MPFDailyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductPriceMapper {
    ProductPriceMapper INSTANCE = Mappers.getMapper(ProductPriceMapper.class);

    @Mapping(target = "currencyUnit", constant = "HKD")
    @Mapping(target = "productId", source = "fundId")
    @Mapping(target = "effectiveDate", source = "item.nav.asOfDate")
    @Mapping(target = "price", source = "item.nav.price")
    ProductPrice convert(MPFDailyResponse item);

    @Mapping(target = "productId", source = "productId")
    ProductPrice convert(ProductPriceRequest productPrice, String productId);

    default OffsetDateTime mapStringToOffsetDateTime(String customDateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss Z");
        return OffsetDateTime.parse(customDateTimeString, formatter);
    }
}
