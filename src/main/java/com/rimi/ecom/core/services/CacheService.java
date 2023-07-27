package com.rimi.ecom.core.services;

import com.rimi.ecom.core.domain.CalculationEntity;
import com.rimi.ecom.core.services.repo.CacheImpl;
import com.rimi.ecom.core.request.CalculatorRequest;
import com.rimi.ecom.core.responces.CalculatorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CacheService {
    @Autowired
    private CacheImpl cacheStorage;

    public void saveToCache(CalculationEntity entity) {
        cacheStorage.save(entity);
    }

    public CalculatorResponse getResultFromCache(CalculatorRequest request) {
        String formula = request.getInput();
        CalculationEntity entity = cacheStorage.findEntityInRepoByFormula(formula).get();
        BigDecimal result = entity.getResult();
        return new CalculatorResponse(result, "Result form cache");
    }

    public boolean isFormulaInCacheStorage(CalculatorRequest request) {
        return cacheStorage.isEntityInRepo(request.getInput());
    }
}
