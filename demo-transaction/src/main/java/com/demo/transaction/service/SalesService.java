package com.demo.transaction.service;

import com.demo.common.dto.DeltaResponse;
import com.demo.transaction.dto.SalesRequest;
import com.demo.transaction.entity.SalesOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SalesService {
    @Transactional
    DeltaResponse updateSalesRequest(List<SalesRequest> request);

    void updateSales(SalesOrder... salesOrder);
}
