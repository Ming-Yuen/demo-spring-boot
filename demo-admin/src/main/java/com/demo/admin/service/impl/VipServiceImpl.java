package com.demo.admin.service.impl;

import com.demo.admin.dao.VipMapper;
import com.demo.admin.entity.VipBonus;
import com.demo.admin.mapping.VipMapping;
import com.demo.admin.service.VipService;
import com.demo.common.dto.VipBonusAdjustmentRequest;
import com.demo.common.util.ValidList;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class VipServiceImpl implements VipService {
    private VipMapping vipMapping;
    private SqlSessionFactory sqlSessionFactory;
    @Override
    public void adjustmentBonusRequest(ValidList<VipBonusAdjustmentRequest> request) {
        updateBonus(vipMapping.convertUpdateBonus(request));
    }
    public void updateBonus(List<VipBonus> vipBonuses){
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)) {
            VipMapper batchMapper = sqlSession.getMapper(VipMapper.class);
            vipBonuses.stream().forEach(vipBonus -> batchMapper.insert(vipBonus));
            sqlSession.commit();
        }
    }
}
