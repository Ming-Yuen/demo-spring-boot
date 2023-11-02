package org.demo.order.service.impl;

import com.demo.common.dto.DeltaResponse;
import com.demo.product.dao.ProductDao;
import com.demo.product.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.demo.order.Dao.SalesDao;
import org.demo.order.Dao.SalesItemDao;
import org.demo.order.dto.SalesRequest;
import org.demo.order.entity.SalesOrder;
import org.demo.order.entity.SalesOrderItem;
import org.demo.order.mapper.SalesMapper;
import org.demo.order.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
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
    private ProductDao productDao;
    @Override
    public DeltaResponse create(List<SalesRequest> request) {
        DeltaResponse response = new DeltaResponse();
        request.parallelStream().forEach(order->{
            try{
                if(salesDao.existsByOrderId(order.getOrderId())) {
                    response.getSuccess().add(order.getOrderId());
                }else{
                    SalesOrder salesOrder = salesMapper.converToSales(order);
                    List<SalesOrderItem> salesOrderItems = salesMapper.convertToSalesItem(order.getSalesItems());
                    salesOrderItems.parallelStream().forEach(orderItem->{
                        Product product = productDao.findBySku(orderItem.getSku());
                        if(product == null){
                            throw new RuntimeException("Item not found, sku : " + orderItem.getSku());
                        }
                    });
                    salesDao.save(salesOrder);
                    salesItemDao.saveAll(salesOrderItems);
                }
            }catch (Exception e){
                log.error("orderId : " + order.getOrderId(), e);
                response.getFailedList().add(new DeltaResponse.ErrorRecord(Collections.singletonMap("orderId", order.getOrderId()), e.getMessage()));
            }
        });
        return response;
    }
}
