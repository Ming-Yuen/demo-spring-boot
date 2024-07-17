package com.demo.batckTask.mapper;

import com.demo.batckTask.dto.BatchTaskUpdateRequest;
import com.demo.batckTask.dto.SalesImportFile;
import com.demo.batckTask.entity.BatchTask;
import com.demo.common.mapper.CustomMapper;
import com.demo.product.entity.Product;
import com.demo.transaction.entity.SalesOrder;
import com.demo.transaction.entity.SalesOrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface BatchTaskMapper {
    BatchTask batchTaskRequestConvertToRequest(BatchTaskUpdateRequest batchTaskUpdateRequest);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "txDatetime", source = "txDatetime", qualifiedByName = "yyyy/MM/dd HH:mm:ss.SSS")
    SalesOrder toSalesOrder(SalesImportFile line);

    SalesOrderItem toSalesOrderItem(SalesImportFile line);

    default List<SalesOrder> convertFileFormat(List<SalesImportFile> salesImportFiles) {
        Map<String, SalesOrder> salesOrderMap = new HashMap<>();

        for (SalesImportFile file : salesImportFiles) {
            SalesOrder salesOrder = salesOrderMap.computeIfAbsent(file.getOrderId(), k -> toSalesOrder(file));
            SalesOrderItem salesOrderItem = toSalesOrderItem(file);
            salesOrder.getItems().add(salesOrderItem);
        }

        return new ArrayList<>(salesOrderMap.values());
    }
    @Mapping(target = "productId", source = "productName")
    @Mapping(target = "enable", defaultValue = "1")
    Product[] toProduct(SalesOrderItem[] salesOrderItems);
}
