package com.demo.product.mapper;

import com.demo.product.dto.manulife.MPFDailyResponse;
import com.demo.product.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface ProductMapper {
    Product[] mpfConvertToProduct(List<MPFDailyResponse> mpfDailyResponse);
}
