package com.demo.order.service;

import com.demo.common.dto.SalesRequest;
import com.demo.common.dto.VipBonusAdjustmentRequest;
import com.demo.order.entity.SalesOrder;

import java.util.List;

public interface SalesService {
    void updateSalesRequest(List<SalesRequest> request);

    void updateSales(List<SalesOrder> salesOrder);

    List<VipBonusAdjustmentRequest>  convertVipBonusAdjustmentRequest(List<SalesRequest> request);
}
