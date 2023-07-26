package com.rimi.ecom.core.responces;

import com.rimi.ecom.core.domain.CalculationEntity;
import lombok.Data;
import java.util.List;

@Data
public class CalculatorResponse {

    private CalculationEntity calculationEntity;
    private List<CoreError> errors;

    public CalculatorResponse(List<CoreError> errors) {
        this.errors = errors;
    }

    public CalculatorResponse(CalculationEntity calculationEntity) {
        this.calculationEntity = calculationEntity;
    }
}
