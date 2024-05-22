package com.demo.product.mapper;

import com.demo.product.entity.Product;
import com.demo.product.vo.ProductUpdateRequest;
import com.demo.product.vo.manulife.MPFDailyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    @Mapping(target = "productId", source = "fundId")
    @Mapping(target = "productName", source = "fundName")
    @Mapping(target = "category", source = "platformName")
    Product convert(MPFDailyResponse mpfDailyResponse);

    Product[] convert(List<ProductUpdateRequest> collect);
}
