package org.demo.order.service.impl;

import com.demo.common.dto.ErrorRecord;
import com.demo.common.exception.ValidationException;
import com.demo.product.dao.ProductDao;
import com.demo.product.entity.Product;
import com.github.javafaker.Bool;
import lombok.extern.slf4j.Slf4j;
import org.demo.order.Dao.SalesDao;
import org.demo.order.Dao.SalesItemDao;
import org.demo.order.dto.SalesRequest;
import org.demo.order.dto.SalesResponse;
import org.demo.order.eneity.Sales;
import org.demo.order.eneity.SalesItem;
import org.demo.order.mapper.SalesMapper;
import org.demo.order.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
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
    public SalesResponse create(List<SalesRequest> request) {
        ConcurrentLinkedQueue<String> success = new ConcurrentLinkedQueue<String>();
        ConcurrentLinkedQueue<ErrorRecord> fail = new ConcurrentLinkedQueue<ErrorRecord>();

        SalesResponse response = new SalesResponse();

        request.parallelStream().forEach(order->{
            try{
                Sales sales = salesDao.findByOrderId(order.getOrderId());
                if(sales != null) {
                    success.add(order.getOrderId());
                }else{
                    sales = salesMapper.converToSales(order);
                    List<SalesItem> salesItems = salesMapper.convertToSalesItem(order.getSalesItems());
                    salesItems.parallelStream().forEach(orderItem->{
                        Product product = productDao.findBySku(orderItem.getSku());
                        if(product == null){
                            throw new RuntimeException("Item not found, sku : " + orderItem.getSku());
                        }
                    });
                    salesDao.save(sales);
                    salesItemDao.saveAll(salesItems);
                }
            }catch (Exception e){
                log.error("orderId : " + order.getOrderId(), e);
                fail.add(new ErrorRecord(order.getOrderId(), e.getMessage()));
            }
        });
        response.setSuccessList(new ArrayList<>(success));
        response.setFailedList(new ArrayList<>(fail));
        return response;
    }
}
