package com.rimi.ecom.rest;

import com.rimi.ecom.core.dto.CalculatorRequest;
import com.rimi.ecom.core.dto.CalculatorResponse;
import com.rimi.ecom.core.services.CacheService;
import com.rimi.ecom.core.services.CalculatorService;
import com.rimi.ecom.core.validators.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PayloadControllerTest {

    @InjectMocks
    private PayloadController payloadController;

    @Mock
    private CacheService cacheService;

    @Mock
    private CalculatorService calculatorService;

    @Mock
    private InputValidator validator;

    private CalculatorRequest request;
    private CalculatorResponse expectedResponse;

    @BeforeEach
    public void setup() {
        request = new CalculatorRequest("5+2");
        expectedResponse = new CalculatorResponse(new BigDecimal(7.0), "Calculated result");
    }

    @Test
    public void testSuccessfulCalculation() {
        // Mock the behavior of the validator
        when(validator.validate(request)).thenReturn(Collections.emptyList());

        // Mock the behavior of the cache service
        when(cacheService.isFormulaInCacheStorage(request)).thenReturn(false);

        // Mock the behavior of the calculator service
        when(calculatorService.calculateResult(request)).thenReturn(expectedResponse);

        // Perform the test
        ResponseEntity<String> result = payloadController.getResult(request);

        // Verify the output
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedResponse.toString(), result.getBody());
    }

    @Test
    public void testCacheHit() {
        // Mock the behavior of the validator
        when(validator.validate(request)).thenReturn(Collections.emptyList());

        // Mock the behavior of the cache service
        when(cacheService.isFormulaInCacheStorage(request)).thenReturn(true);
        when(cacheService.getResultFromCache(request)).thenReturn(expectedResponse);

        // Perform the test
        ResponseEntity<String> result = payloadController.getResult(request);

        // Verify the output
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedResponse.toString(), result.getBody());
    }

    @Test
    public void testCalculationFailure() {
        // Mock the behavior of the validator
        when(validator.validate(request)).thenReturn(Collections.emptyList());

        // Mock the behavior of the cache service
        when(cacheService.isFormulaInCacheStorage(request)).thenReturn(false);

        // Mock the behavior of the calculator service
        when(calculatorService.calculateResult(request)).thenThrow(new IllegalArgumentException("Calculation error."));

        // Perform the test
        ResponseEntity<String> result = payloadController.getResult(request);

        // Verify the output
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertTrue(result.getBody().contains("Error: Calculation error."));
    }
}
