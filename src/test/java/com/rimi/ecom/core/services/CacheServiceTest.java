package com.rimi.ecom.core.services;

import com.rimi.ecom.core.domain.CalculationEntity;
import com.rimi.ecom.core.dto.CalculatorRequest;
import com.rimi.ecom.core.dto.CalculatorResponse;
import com.rimi.ecom.core.services.repo.CacheImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CacheServiceTest {

    @Mock
    private CacheImpl cacheStorage;

    @InjectMocks
    private CacheService cacheService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveToCache() {
        CalculationEntity entity = new CalculationEntity("formula1", BigDecimal.valueOf(42));
        cacheService.saveToCache(entity);
        verify(cacheStorage, times(1)).save(entity);
    }

    @Test
    public void testGetResultFromCache() {
        String formula = "formula2";
        BigDecimal resultValue = BigDecimal.valueOf(99);
        CalculationEntity entity = new CalculationEntity(formula, resultValue);
        CalculatorRequest request = new CalculatorRequest(formula);

        when(cacheStorage.findEntityInRepoByFormula(formula)).thenReturn(Optional.of(entity));

        CalculatorResponse response = cacheService.getResultFromCache(request);

        assertNotNull(response);
        assertEquals(resultValue, response.getResult());
        assertEquals("Result form cache", response.getMessageResponse());
        assertNull(response.getErrors());
    }

    @Test
    public void testGetResultFromCacheNotFound() {
        String formula = "nonexistent_formula";
        CalculatorRequest request = new CalculatorRequest(formula);

        when(cacheStorage.findEntityInRepoByFormula(formula)).thenReturn(Optional.empty());

        CalculatorResponse response = cacheService.getResultFromCache(request);

        assertNotNull(response);
        assertNull(response.getResult());
        assertEquals("Not found in cache", response.getMessageResponse());
        assertNull(response.getErrors());
    }

    @Test
    public void testIsFormulaInCacheStorage() {
        String formula = "formula3";
        CalculatorRequest request = new CalculatorRequest(formula);

        when(cacheStorage.isEntityInRepo(formula)).thenReturn(true);

        assertTrue(cacheService.isFormulaInCacheStorage(request));
    }

    @Test
    public void testIsFormulaNotInCacheStorage() {
        String formula = "nonexistent_formula";
        CalculatorRequest request = new CalculatorRequest(formula);

        when(cacheStorage.isEntityInRepo(formula)).thenReturn(false);

        assertFalse(cacheService.isFormulaInCacheStorage(request));
    }
}



