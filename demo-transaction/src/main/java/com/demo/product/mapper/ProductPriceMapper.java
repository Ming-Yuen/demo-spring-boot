package com.demo.product.mapper;

import com.demo.product.entity.ProductPrice;
import com.demo.product.vo.ProductPriceRequest;
import com.demo.product.vo.ProductUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductPriceMapper {
    ProductPriceMapper INSTANCE = Mappers.getMapper(ProductPriceMapper.class);

    ProductPrice convert(ProductPriceRequest productPrice);
}
