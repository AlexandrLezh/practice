package com.rimi.ecom.core.services.repo;

import com.rimi.ecom.core.domain.CalculationEntity;
import java.util.Optional;

public interface Repository {
    void save(CalculationEntity calculationEntity);

    boolean isEntityInRepo(String formula);

    Optional<CalculationEntity> findEntityInRepoByFormula(String formula);
}
