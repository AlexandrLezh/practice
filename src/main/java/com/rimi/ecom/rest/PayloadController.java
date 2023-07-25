package com.rimi.ecom.rest;

import com.rimi.ecom.core.responces.CalculatorResponse;
import com.rimi.ecom.core.services.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayloadController {
    @Autowired
    private Calculator calculator;

    @PostMapping("/process")
    public void processPayload(@RequestBody String input) {
        CalculatorResponse response = calculator.calculateResult(input);
        System.out.println("Result: " + response.getResult());
    }
}
