package com.demo.transaction.service.impl;

import com.demo.common.dto.DeltaResponse;
import com.demo.common.util.DateUtil;
import com.demo.product.entity.ProductPrice;
import com.demo.product.service.ProductService;
import com.demo.transaction.dao.SalesDao;
import com.demo.transaction.dao.SalesItemDao;
import com.demo.transaction.dto.SalesRequest;
import com.demo.transaction.entity.SalesOrder;
import com.demo.transaction.entity.SalesOrderItem;
import com.demo.transaction.mapper.SalesMapper;
import com.demo.transaction.service.SalesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Slf4j
@Service
public class SalesServiceImpl implements SalesService {
    @Autowired
    private SalesDao salesDao;
    @Autowired
    private SalesMapper salesMapper;
    @Autowired
    private SalesItemDao salesItemDao;
    @Autowired
    private ProductService productService;
    @Override
    public DeltaResponse updateSalesRequest(List<SalesRequest> request) {
        DeltaResponse response = new DeltaResponse();
        request.parallelStream().forEach(order->{
            try{
                SalesOrder salesOrder = salesMapper.requestConvertOrder(order);
                updateSales(salesOrder);
            }catch (Exception e){
                log.error("orderId : " + order.getOrderId(), e);
                response.getFailedList().add(new DeltaResponse.ErrorRecord(Collections.singletonMap("orderId", order.getOrderId()), e.getMessage()));
            }
        });
        return response;
    }

    @Override
    public void updateSales(SalesOrder... salesOrders){
        for(SalesOrder salesOrder : salesOrders){
            if(salesDao.existsByOrderId(salesOrder.getOrderId())) {

            }else{
                salesOrder.getItems().forEach(orderItem->{
                    ProductPrice productPrice = productService.getLatestProductPrice(DateUtil.convertOffsetDatetime(salesOrder.getTxDatetime().toLocalDate()), orderItem.getProductId());
                    if(productPrice == null){
                        throw new RuntimeException("product : " + orderItem.getProductId() + " price not found");
                    }
                });
                salesDao.save(salesOrder);
                salesItemDao.saveAll(salesOrder.getItems());
            }
        }
    }
}
