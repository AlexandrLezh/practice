package com.rimi.ecom.core.services;

import com.rimi.ecom.core.domain.CalculationEntity;
import com.rimi.ecom.core.request.CalculatorRequest;
import com.rimi.ecom.core.responces.CalculatorResponse;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class CacheService {
    private Map<String, BigDecimal> cacheStorage = new HashMap<>();

    public void saveToCache(String formula, BigDecimal result) {
        cacheStorage.put(formula, result);
    }

    public CalculatorResponse getResultFromCache(CalculatorRequest request) {
        BigDecimal result = cacheStorage.get(request.getInput());
        CalculationEntity response = new CalculationEntity(request.getInput(), result, "Result from cache");
        return new CalculatorResponse(response);
    }

    public boolean isInCacheStorage(CalculatorRequest request) {
        return cacheStorage.containsKey(request.getInput());
    }
}
