package com.demo.order.service.impl;

import com.demo.common.annotation.BatchProcess;
import com.demo.common.dto.InventoryAdjustmentRequest;
import com.demo.common.dto.VipBonusAdjustmentRequest;
import com.demo.order.dao.SaleItemMapper;
import com.demo.order.dao.SaleMapper;
import com.demo.order.dao.SalesItemMapper;
import com.demo.common.dto.SalesRequest;
import com.demo.order.entity.SalesOrder;
import com.demo.order.entity.SalesOrderItem;
import com.demo.order.mapper.SalesMapping;
import com.demo.order.service.ProductService;
import com.demo.order.service.SalesService;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SalesServiceImpl implements SalesService {
    private SalesMapping salesMapping;
    private ProductService productService;
    private SqlSessionFactory sqlSessionFactory;
    @Override
    public void updateSalesRequest(List<SalesRequest> request) {
        updateSales(salesMapping.salesRequestToSalesOrder(request));
    }

    @Override
    @Transactional
    @BatchProcess
    public void updateSales(List<SalesOrder> salesOrders){
        String[] orderIds = salesOrders.stream().map(SalesOrder::getOrderId).toArray(String[]::new);
        OffsetDateTime[] txDatetime = salesOrders.stream().map(SalesOrder::getTxDatetime).toArray(OffsetDateTime[]::new);
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)){
            SaleMapper saleMapper = sqlSession.getMapper(SaleMapper.class);
            SaleItemMapper itemBatchMapper = sqlSession.getMapper(SaleItemMapper.class);

            Set<String> presentOrderIds = saleMapper.findByOrderIdIn(salesOrders);

            Map<String, BigDecimal> productPriceMap = productService.findByProductPriceAndEffectiveDate(orderIds,txDatetime);

            for(SalesOrder salesOrder : salesOrders){
                if(presentOrderIds.contains(salesOrder.getOrderId())) {

                }else{
                    for(SalesOrderItem orderItem : salesOrder.getItems()){
                        if(productPriceMap.containsKey(orderItem.getProductId())){
                            throw new RuntimeException("product : " + orderItem.getProductId() + " price not found");
                        }
                    }
                }
                saleMapper.insert(salesOrder);
                salesOrder.getItems().forEach(salesOrderItem -> itemBatchMapper.insert(salesOrderItem));
            }
            sqlSession.commit();
        }
    }

    @Override
    public List<VipBonusAdjustmentRequest> convertVipBonusAdjustmentRequest(List<SalesRequest> request) {
        return salesMapping.salesRequestToVipBonusAdjustmentRequest(request);
    }
}
