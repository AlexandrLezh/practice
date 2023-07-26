package com.rimi.ecom.rest;

import com.rimi.ecom.core.request.CalculatorRequest;
import com.rimi.ecom.core.responces.CalculatorResponse;
import com.rimi.ecom.core.responces.CoreError;
import com.rimi.ecom.core.services.CacheService;
import com.rimi.ecom.core.services.Calculator;
import com.rimi.ecom.core.validators.StringValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class PayloadController {
    @Autowired
    private CacheService cacheService;
    @Autowired
    private Calculator calculator;
    @Autowired
    private StringValidator validator;

    @PostMapping(value = "/calculate",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<String> getResult(@RequestBody CalculatorRequest request) {
        List<CoreError> errors = validator.validate(request);
        if(!errors.isEmpty()) {
            System.out.println(errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
        }
        try {
            if (cacheService.isInCacheStorage(request)) {
                return cacheServiceExecute(request);
            }
            return calculateServiceExecute(request);
        } catch (IllegalArgumentException e) {
            return errorServiceExecute(e);
        }
    }

    private static ResponseEntity<String> errorServiceExecute(IllegalArgumentException e) {
        String errorMessage = "Error: " + e.getMessage();
        System.err.println(errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    private ResponseEntity<String> calculateServiceExecute(CalculatorRequest request) {
        CalculatorResponse result = calculator.calculateResult(request);
        String message = result.getCalculationEntity().getResultFromMessage();
        System.out.println(message + request + " = " + result);
        return ResponseEntity.ok(result.toString());
    }

    private ResponseEntity<String> cacheServiceExecute(CalculatorRequest request) {
        CalculatorResponse result = cacheService.getResultFromCache(request);
        String message = result.getCalculationEntity().getResultFromMessage();
        System.out.println(message + request + " = " + result);
        return ResponseEntity.ok(result.toString());
    }
}

