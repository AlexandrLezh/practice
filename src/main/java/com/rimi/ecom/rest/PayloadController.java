package com.rimi.ecom.rest;

import com.rimi.ecom.core.dto.CalculatorRequest;
import com.rimi.ecom.core.dto.CalculatorResponse;
import com.rimi.ecom.core.validators.CoreError;
import com.rimi.ecom.core.services.CacheService;
import com.rimi.ecom.core.services.CalculatorService;
import com.rimi.ecom.core.validators.InputValidator;
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
    private CalculatorService calculatorService;
    @Autowired
    private InputValidator validator;

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
            if (cacheService.isFormulaInCacheStorage(request)) {
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
        CalculatorResponse response = calculatorService.calculateResult(request);
        consoleOutput(request, response);
        return ResponseEntity.ok(response.toString());
    }

    private ResponseEntity<String> cacheServiceExecute(CalculatorRequest request) {
        CalculatorResponse response = cacheService.getResultFromCache(request);
        consoleOutput(request, response);
        return ResponseEntity.ok(response.toString());
    }

    private static void consoleOutput(CalculatorRequest request, CalculatorResponse response) {
        System.out.println(request.getInput() + " = " + response.getResult() + " " + response.getMessageResponse());
    }
}

