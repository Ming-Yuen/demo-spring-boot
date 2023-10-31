package org.demo.order.dto;

import com.demo.common.dto.DefaultResponse;
import com.demo.common.dto.ErrorRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class SalesResponse extends DefaultResponse {
    private List<String> successList;
    private List<ErrorRecord> failedList;
}
