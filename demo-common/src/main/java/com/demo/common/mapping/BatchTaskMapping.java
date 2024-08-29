package com.demo.common.mapping;

import com.demo.common.dto.BatchTaskUpdateRequest;
import com.demo.common.entity.BatchTask;
import com.demo.common.mapper.CustomMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = CustomMapper.class)
public interface BatchTaskMapping {
    BatchTask batchTaskRequestConvertToRequest(BatchTaskUpdateRequest batchTaskUpdateRequest);
}
