package com.demo.transaction.service.impl;

import com.demo.product.service.ProductService;
import com.demo.transaction.dao.SaleMapper;
import com.demo.transaction.dao.SalesItemMapper;
import com.demo.common.dto.SalesRequest;
import com.demo.transaction.entity.SalesOrder;
import com.demo.transaction.entity.SalesOrderItem;
import com.demo.transaction.mapper.SalesMapping;
import com.demo.transaction.service.SalesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

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
}
