package com.rimi.ecom.core.services.repo;

import com.rimi.ecom.core.domain.CalculationEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CacheImpl implements Repository {
    private final List<CalculationEntity> cache = new ArrayList<>();
    @Override
    public void save(CalculationEntity calculationEntity) {
        cache.add(calculationEntity);
    }

    @Override
    public Optional<CalculationEntity> findEntityInRepoByFormula(String formula) {
        return cache.stream()
                .filter(calculationEntity -> calculationEntity.getFormula().equals(formula))
                .findFirst();
    }
    @Override
    public boolean isEntityInRepo(String formula) {
        Optional<CalculationEntity> resultOptional = findEntityInRepoByFormula(formula);
        return resultOptional.isPresent();
    }
}
