package com.demo.product.mapper;

import com.demo.product.entity.Product;
import com.demo.product.vo.ProductUpdateRequest;
import com.demo.product.vo.manulife.MPFDailyResponse;
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
