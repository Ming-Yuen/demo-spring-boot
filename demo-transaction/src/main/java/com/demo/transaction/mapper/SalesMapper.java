package com.demo.transaction.mapper;

import com.demo.transaction.dto.SalesRequest;
import com.demo.transaction.entity.SalesOrder;
import com.demo.transaction.entity.SalesOrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SalesMapper {

    SalesOrder converToSales(SalesRequest order);

    List<SalesOrderItem> convertToSalesItem(List<SalesRequest.SalesItem> salesItems);
}
