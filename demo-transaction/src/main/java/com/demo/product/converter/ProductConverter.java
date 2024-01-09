package com.demo.product.converter;

import com.demo.product.vo.manulife.MPFDailyResponse;
import com.demo.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ProductConverter {
    @Mapping(target = "productId", source = "fundId")
    @Mapping(target = "region", constant = "HK")
    @Mapping(target = "name", source = "fundName")
    @Mapping(target = "category", source = "platformName")
    Product convert(MPFDailyResponse mpfDailyResponse);
}
