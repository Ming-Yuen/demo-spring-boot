package com.demo.transaction.service.impl;

import com.demo.product.entity.ProductPrice;
import com.demo.product.service.ProductService;
import com.demo.transaction.dao.SalesItemRepository;
import com.demo.transaction.dao.SalesRepository;
import com.demo.transaction.dto.SalesRequest;
import com.demo.transaction.entity.SalesOrder;
import com.demo.transaction.entity.SalesOrderItem;
import com.demo.transaction.mapper.SalesMapper;
import com.demo.transaction.service.SalesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.*;

@Slf4j
@Service
public class SalesServiceImpl implements SalesService {
    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private SalesMapper salesMapper;
    @Autowired
    private SalesItemRepository salesItemRepository;
    @Autowired
    private ProductService productService;
    @Override
    public void updateSalesRequest(List<SalesRequest> request) {
        SalesOrder[] salesOrder = salesMapper.requestConvertOrder(request);
        updateSales(salesOrder);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateSales(SalesOrder... salesOrders){
        List<SalesOrder> salesOrderList = new ArrayList<>();
        String[] orderIds = Arrays.stream(salesOrders).map(SalesOrder::getOrderId).toArray(String[]::new);
        Set<SalesOrder.OrderId> presentOrderIds = salesRepository.findByOrderIdIn(orderIds);

        OffsetDateTime[] txDatetime = Arrays.stream(salesOrders).map(SalesOrder::getTxDatetime).toArray(OffsetDateTime[]::new);
        Map<String, ProductPrice.ProductCurrentPrice> productPriceMap = productService.getLatestProductPrice(orderIds, txDatetime);

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
        salesRepository.saveAll(salesOrderList);
    }
}
