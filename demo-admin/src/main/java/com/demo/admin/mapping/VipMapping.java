package com.demo.admin.mapping;

import com.demo.admin.entity.VipBonus;
import com.demo.common.dto.VipBonusAdjustmentRequest;
import com.demo.common.util.ValidList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface VipMapping {
    List<VipBonus> convertUpdateBonus(ValidList<VipBonusAdjustmentRequest> vipBonusAdjustmentRequestValidList);
    @Mapping(target="bonus", source = "adjBonus")
    VipBonus convertUpdateBonus(VipBonusAdjustmentRequest vipBonusAdjustmentRequest);
}
