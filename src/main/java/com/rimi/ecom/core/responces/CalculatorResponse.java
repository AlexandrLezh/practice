package com.rimi.ecom.core.responces;

import com.rimi.ecom.core.validators.CoreError;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CalculatorResponse {

    private BigDecimal result;
    private String messageResponse;
    private List<CoreError> errors;

    public CalculatorResponse(List<CoreError> errors) {
        this.errors = errors;
    }

    public CalculatorResponse(BigDecimal result, String messageResponse) {
        this.result = result;
        this.messageResponse = messageResponse;
    }
}
