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
public class CalculatorService {

    @Autowired
    private CacheService cacheService;
    public CalculatorResponse calculateResult(CalculatorRequest request) {
        DoubleEvaluator evaluator = new DoubleEvaluator();
        Double result = evaluator.evaluate(request.getInput());
        BigDecimal convertedResult = new BigDecimal(result).setScale(2, RoundingMode.HALF_UP);
        CalculationEntity entity = new CalculationEntity(request.getInput(), convertedResult);
        cacheService.saveToCache(entity);
        return new CalculatorResponse(entity.getResult(), "Calculated result");
    }
}
