package com.rimi.ecom.core.services;

import com.rimi.ecom.core.domain.CalculationEntity;
import com.rimi.ecom.core.services.repo.CacheImpl;
import com.rimi.ecom.core.dto.CalculatorRequest;
import com.rimi.ecom.core.dto.CalculatorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CacheService {
    @Autowired
    private CacheImpl cacheStorage;

    public void saveToCache(CalculationEntity entity) {
        cacheStorage.save(entity);
    }

    public CalculatorResponse getResultFromCache(CalculatorRequest request) {
        String formula = request.getInput();
        Optional<CalculationEntity> entityOptional = cacheStorage.findEntityInRepoByFormula(formula);

        if (entityOptional.isPresent()) {
            CalculationEntity entity = entityOptional.get();
            BigDecimal result = entity.getResult();
            return new CalculatorResponse(result, "Result form cache");
        } else {
            return new CalculatorResponse(null, "Not found in cache");
        }
    }

    public boolean isFormulaInCacheStorage(CalculatorRequest request) {
        return cacheStorage.isEntityInRepo(request.getInput());
    }
}
