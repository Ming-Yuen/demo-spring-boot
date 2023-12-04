package org.demo.order.service.impl;

import com.demo.common.dto.DeltaResponse;
import com.demo.common.util.ContextHolder;
import com.demo.product.entity.Product;
import com.demo.product.entity.ProductPrice;
import com.demo.product.service.ProductService;
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

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private HttpServletRequest servletRequest;
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
                        if(productService.existsByProductId(orderItem.getProductId())){
                            throw new RuntimeException("Item not found, sku : " + orderItem.getProductId());
                        }
                        ProductPrice productPrice = productService.getLatestProductPrice(order.getTxDatetime().toLocalDate(), order.getRegion(), orderItem.getProductId());
//                        if(productPrice == null){
//                            throw new RuntimeException("")
//                        }
                        
                    });
                    salesDao.save(salesOrder);
                    salesItemDao.saveAll(salesOrderItems);


                    //inventory update
                    //bonus
                }
            }catch (Exception e){
                log.error("orderId : " + order.getOrderId(), e);
                response.getFailedList().add(new DeltaResponse.ErrorRecord(Collections.singletonMap("orderId", order.getOrderId()), e.getMessage()));
            }
        });
        return response;
    }
}
