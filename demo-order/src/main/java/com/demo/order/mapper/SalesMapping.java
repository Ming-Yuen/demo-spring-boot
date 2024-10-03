package com.demo.order.mapper;

import com.demo.common.dto.InventoryAdjustmentRequest;
import com.demo.common.dto.SalesImportFile;
import com.demo.common.dto.SalesRequest;
import com.demo.common.dto.VipBonusAdjustmentRequest;
import com.demo.common.mapper.CustomMapper;
import com.demo.order.entity.*;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface SalesMapping {
    List<SalesOrder> salesRequestToSalesOrder(List<SalesRequest> salesRequests);
    default SalesOrder salesRequestToSalesOrder(SalesRequest salesRequest){
        SalesOrder salesOrder = salesRequestToSalesOrder1(salesRequest);
        salesOrder.setItems(salesRequest.getItems().stream().map(salesItem -> salesRequestsItemToSalesOrderItem(salesOrder, salesItem)).collect(Collectors.toList()));
        return salesOrder;
    }

    @Mapping(target = "discount", ignore = true)
    SalesOrderItem salesRequestsItemToSalesOrderItem(SalesOrder salesOrder, SalesRequest.SalesItem salesItem);

    @Named("salesRequestToSalesOrder1")
    SalesOrder salesRequestToSalesOrder1(SalesRequest salesRequest);
    @Mapping(target = "adjQty", source = "qty")
    InventoryAdjustmentRequest convertInventoryRequest(SalesRequest.SalesItem salesItem);
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "txDatetime", source = "txDatetime", qualifiedByName = "yyyy/MM/dd HH:mm:ss.SSS")
    @Mapping(target = "status", constant = "sales")
    @Mapping(target = "paymentMethod", constant = "cash")
    @Mapping(target = "priority", constant = "LOW")
    SalesOrder toSalesOrder(SalesImportFile line);
    @Mapping(target = "txDatetime", source = "salesImportFile.txDatetime", qualifiedByName = "yyyy/MM/dd HH:mm:ss.SSS")
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
    List<Product> toProduct(SalesOrderItem[] salesOrderItems);

    @Mapping(target = "productName", source = "productId")
    @Mapping(target = "enable", constant = "1")
    @Mapping(target = "category", constant = "sales")
    @Mapping(target = "riskRating", constant = "LOW")
    Product toProduct(SalesOrderItem salesOrderItems);

    List<ProductPrice> toProductPrice(SalesOrderItem[] salesOrderItems);
    @Mapping(target = "price", source = "unitPrice")
    @Mapping(target = "effectiveDate", source = "txDatetime")
    ProductPrice toProductPrice(SalesOrderItem salesOrderItems);

    List<Inventory> toProductInventory(SalesOrderItem[] salesOrderItems);

    List<VipBonusAdjustmentRequest> salesRequestToVipBonusAdjustmentRequest(List<SalesRequest> request);
    default VipBonusAdjustmentRequest salesRequestToVipBonusAdjustmentRequest1(SalesRequest salesRequest){
        VipBonusAdjustmentRequest vipBonusAdjustmentRequest = new VipBonusAdjustmentRequest();
        vipBonusAdjustmentRequest.setTxDatetime(salesRequest.getTxDatetime());
        vipBonusAdjustmentRequest.setVipCode(salesRequest.getCustomerName());
        vipBonusAdjustmentRequest.setAdjBonus(salesRequest.getItems().stream().map(salesItem -> salesItem.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add));
        return vipBonusAdjustmentRequest;
    }
}