package com.demo.order.service.impl;

import com.demo.common.dto.InventoryAdjustmentRequest;
import com.demo.order.dao.SaleMapper;
import com.demo.order.dao.SalesItemMapper;
import com.demo.common.dto.SalesRequest;
import com.demo.order.entity.SalesOrder;
import com.demo.order.entity.SalesOrderItem;
import com.demo.order.mapper.SalesMapping;
import com.demo.order.service.ProductService;
import com.demo.order.service.SalesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SalesServiceImpl implements SalesService {
    @Autowired
    private SaleMapper saleMapper;
    @Autowired
    private SalesMapping salesMapping;
    @Autowired
    private SalesItemMapper salesItemMapper;
    @Autowired
    private ProductService productService;
    @Override
    public void updateSalesRequest(List<SalesRequest> request) {
        updateSales(salesMapping.requestConvertOrder(request));
    }

    @Override
    public void updateSales(List<SalesOrder> salesOrders){
        List<SalesOrder> salesOrderList = new ArrayList<>();
        String[] orderIds = salesOrders.stream().map(SalesOrder::getOrderId).toArray(String[]::new);
        Set<String> presentOrderIds = saleMapper.findByOrderIdIn(orderIds);

        OffsetDateTime[] txDatetime = salesOrders.stream().map(SalesOrder::getTxDatetime).toArray(OffsetDateTime[]::new);
        Map<String, BigDecimal> productPriceMap = productService.findByProductPriceAndEffectiveDate(orderIds, txDatetime);

        for(SalesOrder salesOrder : salesOrders){
            if(presentOrderIds.contains(salesOrder.getOrderId())) {

            }else{
                for(SalesOrderItem orderItem : salesOrder.getItems()){
                    if(productPriceMap.containsKey(orderItem.getProductId())){
                        throw new RuntimeException("product : " + orderItem.getProductId() + " price not found");
                    }
                }
            }
            salesOrderList.add(salesOrder);
        }
        saleMapper.insert(salesOrderList);
    }

    @Override
    public List<InventoryAdjustmentRequest> convertInventoryRequest(List<SalesRequest> request) {
        return salesMapping.convertInventoryRequest(request.stream().flatMap(salesRequest -> salesRequest.getSalesItems().stream()).collect(Collectors.toList()));
    }
}
