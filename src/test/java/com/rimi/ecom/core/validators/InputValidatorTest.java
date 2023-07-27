package com.rimi.ecom.core.validators;

import com.rimi.ecom.core.dto.CalculatorRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputValidatorTest {
    private InputValidator inputValidator;

    @BeforeEach
    public void setUp() {
        inputValidator = new InputValidator();
    }

    @Test
    public void testValidateValidInput() {
        CalculatorRequest request = new CalculatorRequest("2+3*4");
        List<CoreError> errors = inputValidator.validate(request);
        assertEquals(0, errors.size(), "No errors should be present for a valid input");
    }

    @Test
    public void testValidateNullInput() {
        CalculatorRequest request = new CalculatorRequest(null);
        List<CoreError> errors = inputValidator.validate(request);
        assertEquals(1, errors.size(), "One error should be present for null input");
        assertEquals("Input", errors.get(0).getFieldTitle());
        assertEquals("Received input is null", errors.get(0).getMessage());
    }

    @Test
    public void testValidateEmptyInput() {
        CalculatorRequest request = new CalculatorRequest("");
        List<CoreError> errors = inputValidator.validate(request);
        assertEquals(2, errors.size(), "One error should be present for empty input");
        assertEquals("Input", errors.get(0).getFieldTitle());
        assertEquals("Received input is Empty", errors.get(0).getMessage());
        assertEquals("Received input contain wrong symbols", errors.get(1).getMessage());
    }

    @Test
    public void testValidateInputWithWrongSymbols() {
        CalculatorRequest request = new CalculatorRequest("2a+3*4");
        List<CoreError> errors = inputValidator.validate(request);
        assertEquals(1, errors.size(), "One error should be present for input with wrong symbols");
        assertEquals("Input", errors.get(0).getFieldTitle());
        assertEquals("Received input contain wrong symbols", errors.get(0).getMessage());
    }

    @Test
    public void testValidateInputWithOnlyMathOperators() {
        CalculatorRequest request = new CalculatorRequest("+-*/");
        List<CoreError> errors = inputValidator.validate(request);
        assertEquals(1, errors.size(), "One error should be present for input with only math operators");
        assertEquals("Input", errors.get(0).getFieldTitle());
        assertEquals("Received input contains only math operators", errors.get(0).getMessage());
    }
}
