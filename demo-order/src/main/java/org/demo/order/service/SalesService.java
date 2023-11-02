package org.demo.order.service;

import com.demo.common.dto.DeltaResponse;
import org.demo.order.dto.SalesRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SalesService {
    @Transactional
    DeltaResponse create(List<SalesRequest> request);
}
