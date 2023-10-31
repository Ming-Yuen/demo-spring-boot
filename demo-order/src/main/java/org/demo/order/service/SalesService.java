package org.demo.order.service;

import org.demo.order.dto.SalesRequest;
import org.demo.order.dto.SalesResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SalesService {
    @Transactional
    SalesResponse create(List<SalesRequest> request);
}
