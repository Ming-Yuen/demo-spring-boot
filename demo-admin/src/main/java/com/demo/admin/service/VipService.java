package com.demo.admin.service;

import com.demo.common.dto.UserRegisterRequest;
import com.demo.common.dto.VipBonusAdjustmentRequest;
import com.demo.common.util.ValidList;

public interface VipService {
    void adjustmentBonusRequest(ValidList<VipBonusAdjustmentRequest> request);
}
