package com.rimi.ecom.core.services;

import com.rimi.ecom.core.domain.CalculationEntity;
import com.rimi.ecom.core.dto.CalculatorRequest;
import com.rimi.ecom.core.dto.CalculatorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CalculatorServiceTest {
    @Mock
    private CacheService cacheService;

    @InjectMocks
    private CalculatorService calculatorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateResult() {
        String input = "2 + 3 * 4";
        double expectedResult = 14.0;
        BigDecimal expectedRoundedResult = new BigDecimal(expectedResult).setScale(2, RoundingMode.HALF_UP);

        CalculatorRequest request = new CalculatorRequest(input);

        // Mock the cacheService.saveToCache method using doNothing()
        CalculationEntity expectedEntity = new CalculationEntity(input, expectedRoundedResult);
        doNothing().when(cacheService).saveToCache(any(CalculationEntity.class));

        CalculatorResponse response = calculatorService.calculateResult(request);

        assertEquals(expectedRoundedResult, response.getResult());
        assertEquals("Calculated result", response.getMessageResponse());

        // Verify that cacheService.saveToCache was called with the correct entity
        verify(cacheService, times(1)).saveToCache(expectedEntity);
    }

}
