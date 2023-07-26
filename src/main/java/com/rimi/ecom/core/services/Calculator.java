package com.rimi.ecom.core.services;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.rimi.ecom.core.domain.CalculationEntity;
import com.rimi.ecom.core.request.CalculatorRequest;
import com.rimi.ecom.core.responces.CalculatorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class Calculator {

    @Autowired
    private CacheService cacheService;
    public CalculatorResponse calculateResult(CalculatorRequest request) {
        CalculatorResponse result = evaluateFormula(request.getInput());
        cacheService.saveToCache(request.getInput(), result.getCalculationEntity().getResult());
        return result;
    }

    private CalculatorResponse evaluateFormula(String input) {
        DoubleEvaluator evaluator = new DoubleEvaluator();
        Double result = evaluator.evaluate(input);
        BigDecimal convertedResult = new BigDecimal(result).setScale(2, RoundingMode.HALF_UP);
        CalculationEntity response = new CalculationEntity(input, convertedResult, "Calculated result");
        return new CalculatorResponse(response);
   }
}
