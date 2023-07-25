package com.rimi.ecom.core.responces;

import lombok.Data;

import java.util.List;
@Data
public class CalculatorResponse {
    private Double result;
    private List<CoreError> errors;

    public CalculatorResponse(List<CoreError> errors) {
        this.errors = errors;
    }

    public CalculatorResponse(Double result) {
        this.result = result;
    }

}
