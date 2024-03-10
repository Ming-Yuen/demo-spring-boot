package com.demo.product.mapper;

import com.demo.product.entity.Product;
import com.demo.product.vo.ProductUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    List<Product> convert(List<ProductUpdateRequest> collect);
}
