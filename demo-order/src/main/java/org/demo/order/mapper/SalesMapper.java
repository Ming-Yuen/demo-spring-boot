package org.demo.order.mapper;

import org.demo.order.dto.SalesRequest;
import org.demo.order.entity.SalesOrder;
import org.demo.order.entity.SalesOrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SalesMapper {

    SalesOrder converToSales(SalesRequest order);

    List<SalesOrderItem> convertToSalesItem(List<SalesRequest.SalesItem> salesItems);
}
