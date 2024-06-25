package com.demo.transaction.service;

import com.demo.transaction.dto.SalesRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SalesService {
    @Transactional
    DeltaResponse create(List<SalesRequest> request);
}
