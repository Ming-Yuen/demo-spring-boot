package com.demo.order.service.impl;

import com.demo.common.annotation.BatchProcess;
import com.demo.common.dto.InventoryAdjustmentRequest;
import com.demo.common.dto.InventoryUpdateRequest;
import com.demo.common.util.LambdaUtil;
import com.demo.order.dao.SaleMapper;
import com.demo.order.mapper.InventoryMapping;
import com.demo.order.dao.InventoryMapper;
import com.demo.order.entity.Inventory;
import com.demo.order.service.InventoryService;
import com.demo.common.vo.InventoryAdjustmentResponse;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class InventoryServiceImpl implements InventoryService {
    private InventoryMapping inventoryMapping;
    private SqlSessionFactory sqlSessionFactory;
    @Override
    public InventoryAdjustmentResponse adjustmentRequest(List<InventoryAdjustmentRequest> request) {
        adjustment(inventoryMapping.adjustmentConvert(request));
        return new InventoryAdjustmentResponse();
    }

    @Override
    @Transactional
    public void adjustment(List<Inventory> inventories) {
        try(SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)){
            InventoryMapper inventoryMapper = sqlSession.getMapper(InventoryMapper.class);
            inventories.stream().forEach(inventory -> inventoryMapper.updateAdjustment(inventory));
            sqlSession.commit();
        }
    }

    @Override
    public InventoryAdjustmentResponse updateRequest(List<InventoryUpdateRequest> request) {
        adjustment(inventoryMapping.inventoryConvert(request));
        return new InventoryAdjustmentResponse();
    }

    @Override
    @BatchProcess
    @Transactional
    public void insert(List<Inventory> inventories) {
        inventories = inventories.stream().filter(LambdaUtil.distinctByKey(Inventory::getProductId)).collect(Collectors.toList());
        try(SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)){
            InventoryMapper inventoryMapper = sqlSession.getMapper(InventoryMapper.class);

            Set<String> inventoryProductIds = inventoryMapper.findByProductIds(inventories);
            inventories.stream().filter(inventory->!inventoryProductIds.contains(inventory.getProductId())).forEach(inventory -> inventoryMapper.insert(inventory));
            sqlSession.commit();
        }
    }
}
