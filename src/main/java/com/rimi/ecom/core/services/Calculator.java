package com.rimi.ecom.core.services;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.rimi.ecom.core.responces.CalculatorResponse;
import com.rimi.ecom.core.responces.CoreError;
import com.rimi.ecom.core.validators.StringValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class Calculator {
    @Autowired
    private StringValidator validator;

    public CalculatorResponse calculateResult(String request) {
        List<CoreError> errors = validator.validate(request);
        if(!errors.isEmpty()) {
            return new CalculatorResponse(errors);
        }
        Double result = evaluateFormula(request);
        return new CalculatorResponse(result);
    }

    private Double evaluateFormula(String input) {
        DoubleEvaluator evaluator = new DoubleEvaluator();
        return evaluator.evaluate(input);
   }
}
