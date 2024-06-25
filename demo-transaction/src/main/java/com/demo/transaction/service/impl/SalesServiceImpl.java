package com.demo.transaction.service.impl;

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
    public void updateSalesRequest(List<SalesRequest> request) {
        SalesOrder[] salesOrder = salesMapper.requestConvertOrder(request);
        updateSales(salesOrder);
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
