package org.demo.order.mapper;

import org.demo.order.dto.SalesRequest;
import org.demo.order.eneity.Sales;
import org.demo.order.eneity.SalesItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SalesMapper {

    Sales converToSales(SalesRequest order);

    List<SalesItem> convertToSalesItem(List<SalesRequest.SalesItem> salesItems);
}
