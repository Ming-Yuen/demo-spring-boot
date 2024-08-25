package com.demo.batckTask.mapper;

import com.demo.common.dto.BatchTaskUpdateRequest;
import com.demo.common.dto.SalesImportFile;
import com.demo.batckTask.entity.BatchTask;
import com.demo.common.mapper.CustomMapper;
import com.demo.product.entity.Inventory;
import com.demo.product.entity.Product;
import com.demo.product.entity.ProductPrice;
import com.demo.transaction.entity.SalesOrder;
import com.demo.transaction.entity.SalesOrderItem;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface BatchTaskMapper {
    BatchTask batchTaskRequestConvertToRequest(BatchTaskUpdateRequest batchTaskUpdateRequest);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "txDatetime", source = "txDatetime", qualifiedByName = "yyyy/MM/dd HH:mm:ss.SSS")
    @Mapping(target = "status", constant = "sales")
    @Mapping(target = "paymentMethod", constant = "cash")
    @Mapping(target = "priority", constant = "LOW")
    SalesOrder toSalesOrder(SalesImportFile line);
    @Mapping(target = "txDatetime", source = "salesImportFile.txDatetime", qualifiedByName = "yyyy/MM/dd HH:mm:ss.SSS")
    @Mapping(target = "salesOrder", source = "salesOrder")
    @Mapping(target = "orderId", source = "salesOrder.orderId")
    @Mapping(target = "itemSequence", source = "sequence")
    @Mapping(target = "discount", constant = "0")
    SalesOrderItem toSalesOrderItem(SalesImportFile salesImportFile, SalesOrder salesOrder, int sequence);

    default List<SalesOrder> convertFileFormat(List<SalesImportFile> salesImportFiles) {
        Map<String, SalesOrder> salesOrderMap = new HashMap<>();

        for (int sequance = 0; sequance < salesImportFiles.size(); sequance++) {
            SalesImportFile file = salesImportFiles.get(sequance);

            SalesOrder salesOrder = salesOrderMap.computeIfAbsent(file.getOrderId(), k -> toSalesOrder(file));
            SalesOrderItem salesOrderItem = toSalesOrderItem(file, salesOrder, sequance + 1);

            salesOrder.setTotalAmount(salesOrder.getTotalAmount().add(salesOrderItem.getAmount()));
            salesOrder.getItems().add(salesOrderItem);
        }

        return new ArrayList<>(salesOrderMap.values());
    }
    Product[] toProduct(SalesOrderItem[] salesOrderItems);

    @Mapping(target = "productName", source = "productId")
    @Mapping(target = "enable", constant = "1")
    @Mapping(target = "category", constant = "sales")
    @Mapping(target = "riskRating", constant = "LOW")
    Product toProduct(SalesOrderItem salesOrderItems);

    ProductPrice[] toProductPrice(SalesOrderItem[] salesOrderItems);
    @Mapping(target = "price", source = "unitPrice")
    @Mapping(target = "effectiveDate", source = "txDatetime")
    ProductPrice toProductPrice(SalesOrderItem salesOrderItems);

    List<Inventory> toProductInventory(SalesOrderItem[] salesOrderItems);
}
