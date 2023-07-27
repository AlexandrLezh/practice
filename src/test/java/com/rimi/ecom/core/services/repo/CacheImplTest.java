package com.rimi.ecom.core.services.repo;

import com.rimi.ecom.core.domain.CalculationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class CacheImplTest {

    private CacheImpl cache;

    @BeforeEach
    public void setUp() {
        cache = new CacheImpl();
    }

    @Test
    public void testSave() {
        CalculationEntity entity = new CalculationEntity("formula1", BigDecimal.valueOf(42));
        cache.save(entity);
        assertTrue(cache.isEntityInRepo("formula1"));
    }

    @Test
    public void testFindEntityInRepoByFormula() {
        BigDecimal value = BigDecimal.valueOf(99);
        CalculationEntity entity = new CalculationEntity("formula2", value);
        cache.save(entity);
        Optional<CalculationEntity> resultOptional = cache.findEntityInRepoByFormula("formula2");
        assertTrue(resultOptional.isPresent());
        assertEquals(value, resultOptional.get().getResult());
    }

    @Test
    public void testIsEntityInRepo() {
        assertFalse(cache.isEntityInRepo("nonexistent_formula"));
        BigDecimal value = BigDecimal.valueOf(15);
        CalculationEntity entity = new CalculationEntity("formula3", value);
        cache.save(entity);
        assertTrue(cache.isEntityInRepo("formula3"));
    }

    @Test
    public void testIsEntityInRepoWhenEmpty() {
        assertFalse(cache.isEntityInRepo("formula4"));
    }
}
