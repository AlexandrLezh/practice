package com.rimi.ecom.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CalculationEntity {
    private String formula;
    private BigDecimal result;
    private String resultFromMessage;
}
