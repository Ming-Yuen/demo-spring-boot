package com.demo.admin.service.impl;

import com.demo.admin.dao.VipMapper;
import com.demo.admin.entity.VipBonus;
import com.demo.admin.mapping.VipMapping;
import com.demo.admin.service.VipService;
import com.demo.common.dto.VipBonusAdjustmentRequest;
import com.demo.common.util.ValidList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VipServiceImpl implements VipService {
    private VipMapping vipMapping;
    private VipMapper vipMapper;
    @Override
    public void adjustmentBonusRequest(ValidList<VipBonusAdjustmentRequest> request) {
        updateBonus(vipMapping.convertUpdateBonus(request));
    }
    public void updateBonus(List<VipBonus> vipBonuses){
        vipMapper.insert(vipBonuses);
    }
}
