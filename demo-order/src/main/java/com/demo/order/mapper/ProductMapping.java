package com.demo.order.mapper;

import com.demo.common.vo.ProductUpdateRequest;
import com.demo.common.vo.MPFDailyResponse;
import com.demo.order.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapping {
    @Mapping(target = "productId", source = "fundId")
    @Mapping(target = "productName", source = "fundName")
    @Mapping(target = "company", source = "platformName")
    @Mapping(target = "category", source = "productLine")
    @Mapping(target = "riskRating", source = "riskRating")
    Product convert(MPFDailyResponse mpfDailyResponse);

    List<Product> convert(List<ProductUpdateRequest> collect);
}
